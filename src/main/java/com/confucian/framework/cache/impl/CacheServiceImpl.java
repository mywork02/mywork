package com.confucian.framework.cache.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.confucian.framework.cache.CacheService;
import com.confucian.framework.cache.support.CASResult;
import com.confucian.framework.cache.support.CacheConstantBase;
import com.confucian.framework.cache.support.StringAsSetCacheHelper;

import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * 缓存 Service
 *
 * @author ice
 */
public class CacheServiceImpl implements CacheService {
    /**
     * logger
     */
    private final transient Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 缓存 memcachedClient
     */
    @Autowired
    private MemcachedClient memcachedClient;

    /**
     * constructor
     */
    public CacheServiceImpl() {
        logger.debug("===========初始化CacheService======");
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    @Override
    public Object get(String key) {
        return memcachedClient.get(key);
    }

    @Override
    public OperationFuture<Boolean> set(String key, int exp, Object o) {
        return this.memcachedClient.set(key, exp, o);
    }

    @Override
    public OperationFuture<Boolean> delete(String key) {
        return this.memcachedClient.delete(key);
    }

    @Override
    public boolean set(Integer time, String key, Object value) {
        boolean result = false;
        if (time == null) {
            throw new IllegalArgumentException("time error!");
        }
        if (StringUtils.isBlank(key) || value == null) {
            return result;
        }
        try {
            result = memcachedClient.set(key, time, value).getStatus().isSuccess();
        } catch (Exception e) {
            logger.warn("", e);
        }
        return result;
    }

    @Override
    public boolean set(String timeKey, String key, Object value) {
        Integer time = CacheConstantBase.getCacheTime(timeKey);
        return set(time, key, value);
    }

    @Override
    public boolean add(Integer time, String key, Object value) {
        boolean result = false;
        if (time == null) {
            throw new IllegalArgumentException("time error!");
        }
        if (StringUtils.isBlank(key) || value == null) {
            return result;
        }
        try {
            result = memcachedClient.add(key, time, value).getStatus().isSuccess();
        } catch (Exception e) {
            logger.warn("", e);
        }
        return result;

    }

    @Override
    public boolean add(String timeKey, String key, Object value) {
        Integer time = CacheConstantBase.getCacheTime(timeKey);
        return add(time, key, value);
    }

    @Override
    public <T> void addElementToSet(String timeKey, String key, T element) {
        if (element == null) {
            return;
        }

        int isContained = hasElementInSet(key, element);
        if (isContained == 1) {
            return;
        }
        boolean isSuccess = false;
        // 经测试，append不需要cas即可保证原子性，但不能保证并发顺序，在此函数中，我们不需要保证并发的顺序
        int times = 0;
        do {
            times++;
            if (isContained == -1) {
                isSuccess = add(timeKey, key, StringAsSetCacheHelper.serializeToString(element, false));
                isContained = 0;
            } else {
                // 在key不存在的情况下，不可以直接append
                isSuccess = memcachedClient.append(0, key, StringAsSetCacheHelper.serializeToString(element, false)).getStatus().isSuccess();
            }
        } while (!isSuccess && (times < 5));
    }

    /**
     * hasElementInSet
     *
     * @param key
     * @param element
     * @return hasElementInSet
     */
    private <T> int hasElementInSet(String key, T element) {
        String strResult = (String) get(key);
        int result = -1;
        if (StringUtils.isNotBlank(strResult)) {
            result = StringAsSetCacheHelper.containsElementInString(strResult, element) ? 1 : 0;
        }
        return result;
    }

    @Override
    public boolean remove(String key) {
        if (StringUtils.isNotBlank(key)) {
            return memcachedClient.delete(key).getStatus().isSuccess();
        }
        return false;
    }

    @Override
    public void removeAsync(String key) {
        if (StringUtils.isNotBlank(key)) {
            memcachedClient.delete(key);
        }
    }

    @Override
    public <T> void removeFromSet(String key, T element) {
        if (element == null) {
            return;
        }

        int isContained = hasElementInSet(key, element);
        if (isContained != 1) {
            return;
        }
        boolean isSuccess = false;
        // 经测试，append不需要cas即可保证原子性，但不能保证并发顺序，在此函数中，我们不需要保证并发的顺序
        do {
            isSuccess = memcachedClient.append(0, key, StringAsSetCacheHelper.serializeToString(element, true)).getStatus().isSuccess();
        } while (!isSuccess);
    }

    @Override
    public int countElementNoInSet(String key) {
        String strResult = (String) get(key);
        return StringAsSetCacheHelper.countElementInString(strResult);
    }

    @Override
    public Map<String, Object> getBulk(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        try {
            return memcachedClient.getBulk(keys);
        } catch (Exception e) {
            logger.warn("", e);
        }
        return null;
    }

    @Override
    public <T> Set<T> getSet(String key, Class<T> clazz) {
        String strResult = (String) get(key);
        Set<T> result = new HashSet<T>();
        boolean needCompact = StringAsSetCacheHelper.convertStringToSet(strResult, clazz, result);
        // 当-超过一定阈值时，需要进行压缩
        if (needCompact) {
            CASResult<Object> casResult = getByCAS(key);
            Set<T> newResult = new HashSet<T>();
            StringAsSetCacheHelper.convertStringToSet((String) casResult.getValue(), clazz, newResult);
            String newString = StringAsSetCacheHelper.serializeCollectionToString(newResult);
            CASResult<Object> casRequest = new CASResult<Object>(casResult.getCas(), newString);
            setByCAS(-1, key, casRequest);
        }
        return result;
    }

    @Override
    public CASResult<Object> getByCAS(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        CASValue<Object> values = memcachedClient.gets(key);

        return new CASResult<Object>(values);
    }

    @Override
    public boolean setByCAS(Integer time, String key, CASResult<Object> value) {
        if (time == null) {
            throw new IllegalArgumentException("timeKey error!");
        }
        if (StringUtils.isBlank(key) || value == null || value.getValue() == null) {
            return false;
        }

        CASResponse casResponse = memcachedClient.cas(key, value.getCas(), value.getValue());
        return casResponse.equals(CASResponse.OK);
    }
}

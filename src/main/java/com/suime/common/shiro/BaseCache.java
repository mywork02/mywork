/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.suime.common.shiro;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.confucian.framework.ioc.SpringContext;
import com.suime.common.cache.CacheService;
import com.suime.common.cache.support.CacheConstantBase;

/**
 * Shiro {@link Cache} implementation that wraps an {@link net.sf.ehcache.Ehcache} instance.
 *
 * @param <K>
 * @param <V>
 * @since 0.2
 */
@Component
public final class BaseCache<K, V> implements Cache<K, V> {

    /**
     * Private internal log instance.
     */
	private static final Logger LOG = LoggerFactory.getLogger(BaseCache.class);

    /**
     * shiro
     */
    private static final String PREFIX_SHIRO_SESSION = "shiro:";

    /**
     * shiro:sessions:all
     */
    private static final String SHIRO_SESSIONS_SET = "shiro:sessions:all";

    /**
     * cacheService
     */
    private CacheService cacheService;

    /**
     * session过期时间,以秒为但会
     */
    private Integer timeOut;

    /**
     * constructor
     */
    private BaseCache() {
    }

    /**
     * InstanceHolder
     *
     * @author ice
     */
    private static class InstanceHolder {

        /**
         * instance
         */
        @SuppressWarnings("rawtypes")
        private static final BaseCache INSTANCE = new BaseCache();
    }

    @SuppressWarnings("rawtypes")
    public static BaseCache getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * ensureCacheInstance
     *
     * @throws CacheException
     */
    private void ensureCacheInstance() throws CacheException {
        if (cacheService != null) {
            return;
        }
        // cacheService = SpringFactory.getBean("cacheService");
        cacheService = (CacheService) SpringContext.getBean("cacheService");
        if (cacheService == null) {
            throw new CacheException("cache Instance in Shiro is null");
        }
    }

    /**
     * Gets a value of an element which matches the given key.
     *
     * @param key the key of the element to return.
     * @return The value placed into the cache with an earlier put, or null if not found or expired
     */
    @Override
    @SuppressWarnings("unchecked")
    public V get(K key) throws CacheException {
        if (key == null) {
            return null;
        }
        ensureCacheInstance();
        V value = (V) cacheService.get(PREFIX_SHIRO_SESSION + key.toString());
        if (value instanceof Session) {
            Session session = (Session) value;
            session.touch();
            cacheService.set(getTimeOut(session), PREFIX_SHIRO_SESSION + key.toString(), session);
        }
        return value;
    }

    /**
     * getTimeOut
     *
     * @param session
     * @return time
     */
    private int getTimeOut(Session session) {
        int time = 0;
        long timeout = session.getTimeout();
        if (timeout <= 0) {
            time = 0;
        } else {
            time = (int) (timeout / 1000);
        }
        return time;
    }

    /**
     * Puts an object into the cache.
     *
     * @param key   the key.
     * @param value the value.
     */
    @Override
    public V put(K key, V value) throws CacheException {
        ensureCacheInstance();
        int time = 0;
        if (value instanceof Session) {
            time = getTimeOut((Session) value);
        }
        boolean result = cacheService.set(time, PREFIX_SHIRO_SESSION + key.toString(), value);
        if (!result) {
            throw new CacheException("Can't put the key " + key + " into the cache");
        }
        org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info(key.toString());
        cacheService.addElementToSet(CacheConstantBase.TIME_FOREVER, SHIRO_SESSIONS_SET, key.toString());
        return value;
    }

    /**
     * Removes the element which matches the key.
     * <p/>
     * <p>If no element matches, nothing is removed and no Exception is thrown.</p>
     *
     * @param key the key of the element to remove
     */
    @Override
    public V remove(K key) throws CacheException {
        V obj = get(key);
        cacheService.removeAsync(PREFIX_SHIRO_SESSION + key.toString());
        cacheService.removeFromSet(SHIRO_SESSIONS_SET, key.toString());
        return obj;
    }

    /**
     * 从cache的调用记录来看，没有使用，因此暂不实现
     */
    @Override
    public void clear() throws CacheException {
        cacheService.remove(SHIRO_SESSIONS_SET);
    }

    /**
     * 从cache的调用记录来看，没有使用，因此暂不实现
     */
    @Override
    public int size() {
        return cacheService.countElementNoInSet(SHIRO_SESSIONS_SET);
    }

    /**
     * 从cache的调用记录来看，没有使用，因此暂不实现
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        ensureCacheInstance();
        try {
            Set<String> shiroKeys = cacheService.getSet(SHIRO_SESSIONS_SET, String.class);
            return (Set<K>) shiroKeys;
        } catch (UnsupportedOperationException e) {
            LOG.warn("keys function is not implemented in MemcachedCache");
            return null;
        }
    }

    /**
     * 需要实现
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<V> values() {
        ensureCacheInstance();
        try {
            Set<String> shiroKeys = cacheService.getSet(SHIRO_SESSIONS_SET, String.class);
            Map<String, Object> map = cacheService.getBulk(shiroKeys);
            return (Collection<V>) map.values();
        } catch (UnsupportedOperationException e) {
            LOG.warn("values function is not implemented in MemcachedCache");
            return null;
        }
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
}

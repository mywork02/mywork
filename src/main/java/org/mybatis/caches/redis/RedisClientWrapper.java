package org.mybatis.caches.redis;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.confucian.framework.cache.CacheService;
import com.confucian.framework.ioc.SpringContext;
import org.apache.ibatis.cache.CacheException;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

public class RedisClientWrapper {
    /**
     * This class log.
     */
    private static final Log LOG = LogFactory.getLog(RedisClientWrapper.class);

    private final RedisConfiguration configuration;
//    private CacheService cacheService = SpringFactory.getBean("cacheService");

    public RedisClientWrapper() {
        configuration = RedisConfigurationBuilder.getInstance().parseConfiguration();
        try {
//            cacheService = SpringFactory.getBean("cacheService");
        } catch (Exception e) {
            String message = "Impossible to instantiate a new redis client instance, see nested exceptions";
            LOG.error(message, e);
            throw new RuntimeException(message, e);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Running new Redis client using " + configuration);
        }
    }

    /**
     * Converts the MyBatis object key in the proper string representation.
     *
     * @param key the MyBatis object key.
     * @return the proper string representation.
     */
    private String toKeyString(final Object key) {
        // issue #1, key too long
        String keyString = configuration.getKeyPrefix() + org.mybatis.caches.redis.StringUtils.sha1Hex(key.toString());
        if (LOG.isDebugEnabled()) {
            LOG.debug("Object key '"
                    + key
                    + "' converted in '"
                    + keyString
                    + "'");
        }
        return keyString;
    }

    /**
     * @param key
     * @return
     */
    public Object getObject(Object key) {
        String keyString = toKeyString(key);
        Object ret = retrieve(keyString);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Retrived object ("
                    + keyString
                    + ", "
                    + ret
                    + ")");
        }

        return ret;
    }

    /**
     * Return the stored group in Redis identified by the specified key.
     *
     * @param groupKey the group key.
     * @return the group if was previously stored, null otherwise.
     */
    @SuppressWarnings("unchecked")
    private Set<String> getGroup(String groupKey) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Retrieving group with id '"
                    + groupKey
                    + "'");
        }

        Object groups = null;
        try {
            groups = retrieve(groupKey);
        } catch (Exception e) {
            LOG.error("Impossible to retrieve group '"
                    + groupKey
                    + "' see nested exceptions", e);
        }

        if (groups == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Group '"
                        + groupKey
                        + "' not previously stored");
            }
            return new HashSet<String>();
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("retrieved group '"
                    + groupKey
                    + "' with values "
                    + groups);
        }
        return (Set<String>) groups;
    }

    /**
     * @param keyString
     * @return
     * @throws Exception
     */
    private Object retrieve(final String keyString) {
        Object retrieved = null;
        CacheService cacheService = (CacheService) SpringContext.getBean("cacheService");
        retrieved = cacheService.get(keyString);
        return retrieved;
    }

    public void putObject(Object key, Object value, String id) {
        String keyString = toKeyString(key);
        String groupKey = toKeyString(id);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Putting object ("
                    + keyString
                    + ", "
                    + value
                    + ")");
        }

        storeInRedis(keyString, value);

        // add namespace key into memcached
        Set<String> group = getGroup(groupKey);
        group.add(keyString);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Insert/Updating object ("
                    + groupKey
                    + ", "
                    + group
                    + ")");
        }

        storeInRedis(groupKey, group);
    }

    /**
     * Stores an object identified by a key in Redis.
     *
     * @param keyString the object key
     * @param value     the object has to be stored.
     */
    private void storeInRedis(String keyString, Object value) {
        if (value != null
                && !Serializable.class.isAssignableFrom(value.getClass())) {
            throw new CacheException("Object of type '"
                    + value.getClass().getName()
                    + "' that's non-serializable is not supported by Redis");
        }
        CacheService cacheService = (CacheService) SpringContext.getBean("cacheService");
        cacheService.set(configuration.getExpiration(), keyString, value);
    }

    public Object removeObject(Object key) {
        String keyString = toKeyString(key);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Removing object '"
                    + keyString
                    + "'");
        }

        Object result = getObject(key);
        if (result != null) {
            CacheService cacheService = (CacheService) SpringContext.getBean("cacheService");
            cacheService.remove(keyString);
        }
        return result;
    }

    public void removeGroup(String id) {
        String groupKey = toKeyString(id);

        Set<String> group = getGroup(groupKey);

        if (group == null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("No need to flush cached entries for group '"
                        + id
                        + "' because is empty");
            }
            return;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Flushing keys: " + group);
        }
        CacheService cacheService = (CacheService) SpringContext.getBean("cacheService");
        for (String key : group) {
            cacheService.remove(key);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Flushing group: " + groupKey);
        }
        cacheService.remove(groupKey);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}

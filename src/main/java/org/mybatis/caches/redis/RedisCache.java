package org.mybatis.caches.redis;

import java.util.concurrent.locks.ReadWriteLock;

import org.apache.ibatis.cache.Cache;

public class RedisCache implements Cache {

    private static final RedisClientWrapper REDIS_CLIENT = new RedisClientWrapper();

    /**
     * The {@link ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new DummyReadWriteLock();

    /**
     * The cache id.
     */
    private final String id;

    /**
     * Builds a new Redis-based Cache.
     *
     * @param id the Mapper id.
     */
    public RedisCache(final String id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        REDIS_CLIENT.removeGroup(this.id);
    }

    /**
     * {@inheritDoc}
     */
    public String getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    public Object getObject(Object key) {
        return REDIS_CLIENT.getObject(key);
    }

    /**
     * {@inheritDoc}
     */
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    /**
     * {@inheritDoc}
     */
    public int getSize() {
        return Integer.MAX_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    public void putObject(Object key, Object value) {
        REDIS_CLIENT.putObject(key, value, this.id);
    }

    /**
     * {@inheritDoc}
     */
    public Object removeObject(Object key) {
        return REDIS_CLIENT.removeObject(key);
    }
}

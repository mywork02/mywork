package org.mybatis.caches.redis;

import org.apache.ibatis.cache.decorators.LoggingCache;

public class LoggingRedisCache extends LoggingCache {
    public LoggingRedisCache(final String id) {
        super(new RedisCache(id));
    }
}

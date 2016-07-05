package org.mybatis.caches.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RedisConfigurationBuilder {
    /**
     * This class instance.
     */
    private static final RedisConfigurationBuilder INSTANCE = new RedisConfigurationBuilder();

    private static final String SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME = "redis.properties.filename";

    /**
     *
     */
    private static final String REDIS_RESOURCE = "spring/mybatis-redis.properties";

    private final String redisPropertiesFilename;

    /**
     * The setters used to extract properties.
     */
    private final List<AbstractPropertySetter<?>> settersRegistry = new ArrayList<AbstractPropertySetter<?>>();

    /**
     * Hidden constructor, this class can't be instantiated.
     */
    private RedisConfigurationBuilder() {
        redisPropertiesFilename = System.getProperty(SYSTEM_PROPERTY_REDIS_PROPERTIES_FILENAME, REDIS_RESOURCE);

        settersRegistry.add(new StringPropertySetter("org.mybatis.caches.redis.keyprefix", "keyPrefix", "_mybatis_"));

//        settersRegistry.add(new IntegerPropertySetter("org.mybatis.caches.redis.expiration", "expiration", 60 * 60 * 24 * 30));
        settersRegistry.add(new IntegerPropertySetter("org.mybatis.caches.redis.expiration", "expiration", 60 * 5));
        settersRegistry.add(new IntegerPropertySetter("org.mybatis.caches.redis.timeout", "timeout", 5));
        settersRegistry.add(new TimeUnitSetter());

        settersRegistry.add(new BooleanPropertySetter("org.mybatis.caches.redis.asyncget", "usingAsyncGet", false));
        settersRegistry.add(new BooleanPropertySetter("org.mybatis.caches.redis.compression", "compressionEnabled", false));
    }

    /**
     * Return this class instance.
     *
     * @return this class instance.
     */
    public static RedisConfigurationBuilder getInstance() {
        return INSTANCE;
    }

    /**
     * Parses the Config and builds a new {@link RedisConfiguration}.
     *
     * @return the converted {@link RedisConfiguration}.
     */
    public RedisConfiguration parseConfiguration() {
        return parseConfiguration(getClass().getClassLoader());
    }

    /**
     * Parses the Config and builds a new {@link RedisConfiguration}.
     *
     * @param the {@link ClassLoader} used to load the {@code redis.properties} file in classpath.
     * @return the converted {@link RedisConfiguration}.
     */
    public RedisConfiguration parseConfiguration(ClassLoader classLoader) {
        Properties config = new Properties();

        // load the properties specified from /redis.properties, if present
        InputStream input = classLoader.getResourceAsStream(redisPropertiesFilename);
        if (input != null) {
            try {
                config.load(input);
            } catch (IOException e) {
                throw new RuntimeException("An error occurred while reading classpath property '"
                        + redisPropertiesFilename
                        + "', see nested exceptions", e);
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    // close quietly
                }
            }
        }

        RedisConfiguration redisConfiguration = new RedisConfiguration();

        for (AbstractPropertySetter<?> setter : settersRegistry) {
            setter.set(config, redisConfiguration);
        }

        return redisConfiguration;
    }
}

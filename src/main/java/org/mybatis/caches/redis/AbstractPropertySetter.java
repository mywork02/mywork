/*
 *    Copyright 2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.caches.redis;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Converts a keyed property string in the Config to a proper Java
 * object representation.
 *
 * @author Simone Tripodi
 */
abstract class AbstractPropertySetter<T> {

    /**
     * 'propertyName'='writermethod' index of {@link RedisConfiguration}
     * properties.
     */
    private static Map<String, Method> WRITERS = new HashMap<String, Method>();

    static {
        try {
            BeanInfo redisConfigInfo = Introspector.getBeanInfo(RedisConfiguration.class);
            for (PropertyDescriptor descriptor : redisConfigInfo.getPropertyDescriptors()) {
                WRITERS.put(descriptor.getName(), descriptor.getWriteMethod());
            }
        } catch (IntrospectionException e) {
            // handle quietly
        }
    }

    /**
     * The Config property key.
     */
    private final String propertyKey;

    /**
     * The {@link RedisConfiguration} property name.
     */
    private final String propertyName;

    /**
     * The {@link RedisConfiguration} property method writer.
     */
    private final Method propertyWriterMethod;

    /**
     * The default value used if something goes wrong during the conversion or
     * the property is not set in the config.
     */
    private final T defaultValue;

    /**
     * Build a new property setter.
     *
     * @param propertyKey the Config property key.
     * @param propertyName the {@link RedisConfiguration} property name.
     * @param defaultValue the property default value.
     */
    public AbstractPropertySetter(final String propertyKey, final String propertyName, final T defaultValue) {
        this.propertyKey = propertyKey;
        this.propertyName = propertyName;

        this.propertyWriterMethod = WRITERS.get(propertyName);
        if (this.propertyWriterMethod == null) {
            throw new RuntimeException("Class '"
                    + RedisConfiguration.class.getName()
                    + "' doesn't contain a property '"
                    + propertyName
                    + "'");
        }

        this.defaultValue = defaultValue;
    }

    /**
     * Extract a property from the, converts and puts it to the
     * {@link RedisConfiguration}.
     *
     * @param config the Config
     * @param redisConfiguration the {@link RedisConfiguration}
     */
    public final void set(Properties config, RedisConfiguration redisConfiguration) {
        String propertyValue = config.getProperty(propertyKey);
        T value;

        try {
            value = this.convert(propertyValue);
            if (value == null) {
                value = defaultValue;
            }
        } catch (Exception e) {
            value = defaultValue;
        }

        try {
            propertyWriterMethod.invoke(redisConfiguration, value);
        } catch (Exception e) {
            throw new RuntimeException("Impossible to set property '"
                    + propertyName
                    + "' with value '"
                    + value
                    + "', extracted from ('"
                    + propertyKey
                    + "'="
                    + propertyValue
                    + ")", e);
        }
    }

    /**
     * Convert a string representation to a proper Java Object.
     *
     * @param value the value has to be converted.
     * @return the converted value.
     * @throws Exception if any error occurs.
     */
    protected abstract T convert(String value) throws Exception;

}

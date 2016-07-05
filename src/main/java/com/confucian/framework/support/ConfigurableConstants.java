package com.confucian.framework.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * configurableConstants
 * @author ice
 */
public class ConfigurableConstants {
	/**
	 * logger
	 */
	protected static Logger logger = LoggerFactory.getLogger(ConfigurableConstants.class);

	/**
	 * properties
	 */
	protected static Properties prop = new Properties();

	/**
	 * init
	 * @param propertyFileName
	 */
	protected static void init(String propertyFileName) {
		InputStream in = ConfigurableConstants.class.getClassLoader().getResourceAsStream(propertyFileName);
		if (in != null) {
			try {
				prop.load(in);
			} catch (IOException e) {
				logger.error("load " + propertyFileName + " into Constants error!", e);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close " + propertyFileName + " error!", e);
				}
			}
		}
	}

	/**
	 * getProperty
	 * @param key key
	 * @param defaultValue defaultValue 找不到对应的key值时的默认值
	 * @return text
	 */
	protected static String getProperty(String key, String defaultValue) {
		return prop.getProperty(key, defaultValue);
	}
}

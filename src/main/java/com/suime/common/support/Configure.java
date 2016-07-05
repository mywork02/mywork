package com.suime.common.support;

import com.confucian.framework.support.ConfigurableConstants;

/**
 * 相关读取资源文件信息
 * @author ice
 */
public class Configure extends ConfigurableConstants {

	static {
		init("config/configure.properties");
	}

	/**
	 * 获取配置信息
	 * @param key key
	 * @return 对应value
	 */
	public static String getPropertyValue(String key) {
		return getProperty(key, null);
	}

	/**
	 * 获取配置信息
	 * @param key key
	 * @param defaultValue defaultValue
	 * @return 对应value
	 */
	public static String getPropertyValue(String key, String defaultValue) {
		return getProperty(key, defaultValue);
	}
}

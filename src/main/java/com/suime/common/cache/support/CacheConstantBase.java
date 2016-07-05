package com.suime.common.cache.support;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.UnmodifiableMap;

/**
 * Memcached 缓存常量类
 * @author ice
 */
@SuppressWarnings("unchecked")
public abstract class CacheConstantBase {
	//
	// /**
	// * 缓存中用户名和用户信息的键值前缀
	// */
	// public static final String PREFIX_USERNAME_USER = "username_user_";
	// // 缓存用户id和用户信息的键值前缀
	// public static final String PREFIX_USEID_USER = UserBean.class.getName();
	// /**
	// * 缓存用户登录名和用户id的键值前缀
	// */
	// public static final String PREFIX_LOGIN_NAME_USERID = "login_name_userid_";

	/**
	 * 缓存时间常量,永久保存
	 */
	public static final String TIME_FOREVER = "forever";
	/**
	 * 半分钟(30秒)
	 */
	public static final String TIME_HALFMIN = "halfMin";
	/**
	 * 一分钟
	 */
	public static final String TIME_ONEMIN = "oneMin";
	/**
	 * 3分钟
	 */
	public static final String TIME_THREEMIN = "threeMin";
	/**
	 * 10分钟
	 */
	public static final String TIME_TENMIN = "tenMin";
	/**
	 * 20分钟
	 */
	public static final String TIME_TWENTYMIN = "twentyMin";
	/**
	 * 半小时
	 */
	public static final String TIME_HALFHOUR = "halfHour";
	/**
	 * 一小时
	 */
	public static final String TIME_ONEHOUR = "oneHour";
	/**
	 * 2小时
	 */
	public static final String TIME_TWOHOUR = "twoHour";
	/**
	 * 半天(12小时)
	 */
	public static final String TIME_HALFDAY = "halfDay";
	/**
	 * 一天(24小时)
	 */
	public static final String TIME_ONEDAY = "oneDay";
	/**
	 * 3天
	 */
	public static final String TIME_THREEDAY = "threeDay";
	/**
	 * 一周(7天)
	 */
	public static final String TIME_SEVENDAY = "sevenDay";
	/**
	 * 一个月
	 */
	public static final String TIME_ONE_MONTH = "oneMonth";
	/**
	 * 时间时长 map
	 */
	private static final Map<String, Integer> CACHE_TIME_MAP;

	static {
		final int fifteen = 15;
		final int twenty = 20;
		final int thirty = 30;
		Map<String, Integer> tempMap = new HashMap<String, Integer>(fifteen);
		tempMap.put(TIME_FOREVER, 0);
		tempMap.put(TIME_HALFMIN, thirty);
		tempMap.put(TIME_ONEMIN, 60);
		tempMap.put(TIME_THREEMIN, 60 * 3);
		tempMap.put(TIME_TENMIN, 60 * 10);
		tempMap.put(TIME_TWENTYMIN, 60 * twenty);
		tempMap.put(TIME_HALFHOUR, 60 * thirty);
		tempMap.put(TIME_ONEHOUR, 60 * 60);
		tempMap.put(TIME_TWOHOUR, 60 * 60 * 2);
		tempMap.put(TIME_HALFDAY, 60 * 60 * 12);
		tempMap.put(TIME_ONEDAY, 60 * 60 * 24);
		tempMap.put(TIME_THREEDAY, 60 * 60 * 24 * 3);
		tempMap.put(TIME_SEVENDAY, 60 * 60 * 24 * 7);
		tempMap.put(TIME_ONE_MONTH, 60 * 60 * 24 * thirty);
		CACHE_TIME_MAP = UnmodifiableMap.decorate(tempMap);
	}

	/**
	 * 根据 字符串 获取时长.如:TIME_ONEMIN(oneMin):60
	 * @param key key
	 * @return 时长
	 */
	public static Integer getCacheTime(String key) {
		return CACHE_TIME_MAP.get(key);
	}

	/**
	 * 根据属性名和id值生成key
	 * @param objectType
	 * @param id
	 * @return key
	 */
	public static <T> String generateKeyFromId(Class<T> objectType, Serializable id) {
		return objectType.getName() + "_" + id.toString();
	}

	/**
	 * 根据属性名生成key
	 * @param objectType
	 * @param keyName
	 * @param keyValue
	 * @return key
	 */
	public static <T> String generateKeyFromField(Class<T> objectType, String keyName, Serializable keyValue) {
		return objectType.getName() + "_" + keyName + "_" + keyValue;
	}

}

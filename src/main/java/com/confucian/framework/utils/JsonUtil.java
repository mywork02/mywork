package com.confucian.framework.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * jsonUtil 简单封装 alibaba fastjson
 * @author ice
 */
public class JsonUtil {

	/**
	 * 对象转化为json对象
	 * @param object object
	 * @return json对象
	 */
	public static JSONObject toJsonObject(Object object) {
		if (object instanceof JSONObject) {
			return (JSONObject) object;
		} else if (object instanceof String) {
			return JSON.parseObject(object.toString());
		} else {
			return JSON.parseObject(toJsonString(object));
		}

	}

	/**
	 * 转化为json字符串
	 * @param object object
	 * @return json字符串
	 */
	public static String toJsonString(Object object) {
		return JSON.toJSONString(object);
	}

	/**
	 * 对象转化为json数组对象
	 * @param object object
	 * @return json数组对象
	 */
	public static JSONArray toJsonArray(Object object) {
		if (object instanceof JSONArray) {
			return (JSONArray) object;
		} else if (object instanceof String) {
			return JSON.parseArray(object.toString());
		} else {
			return JSON.parseArray(toJsonString(object));
		}
	}

	/**
	 * parseObject
	 * @param text
	 * @param clazz
	 * @return T
	 */
	public static <T> T parseObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	/**
	 * parseArray
	 * @param text
	 * @param clazz
	 * @return list T
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	/**
	 * toJavaObject
	 * @param json
	 * @param clazz
	 * @return javaObject
	 */
	public static <T> T toJavaObject(JSON json, Class<T> clazz) {
		return TypeUtils.cast(json, clazz, ParserConfig.getGlobalInstance());
	}
}

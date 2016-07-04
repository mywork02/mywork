package com.suime.library.shiro;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by ice on 8/1/2016.
 */
public class IosHelper {

	/**
	 * parseKeyValue
	 * @param str
	 * @return keyValueBean
	 */
	public static KeyValueBean parseKeyValue(String str) {
		KeyValueBean keyValueBean = null;
		List<String> keysList = new ArrayList<>();
		List<String> valuesList = new ArrayList<>();
		if (StringUtils.isNotBlank(str)) {
			if (StringUtils.startsWithIgnoreCase(str.trim(), "{")) {
				JSONObject jsonObject = JsonUtil.toJsonObject(str);
				keyValueBean = json(jsonObject);
			} else if (StringUtils.startsWithIgnoreCase(str.trim(), "[")) {
				JSONArray jsonArray = JsonUtil.toJsonArray(str);
				keyValueBean = json(jsonArray);
			}
			if (keyValueBean != null) {
				List<String> keys = keyValueBean.getKeys();
				if (keys != null && !keys.isEmpty()) {
					keysList.addAll(keys);
				}
				List<String> values = keyValueBean.getValues();
				if (values != null && !values.isEmpty()) {
					valuesList.addAll(values);
				}
			}
		}
		return keyValueBean;
	}

	/**
	 * json
	 * @param json
	 * @return KeyValueBean
	 */
	private static KeyValueBean json(JSON json) {
		if (json == null) {
			return null;
		}
		KeyValueBean keyValueBean = new KeyValueBean();
		List<String> keysList = new ArrayList<>();
		List<String> valuesList = new ArrayList<>();

		if (json instanceof JSONObject) {
			parse2KeyValue(json, keysList, valuesList);
		} else if (json instanceof JSONArray) {
			parseJsonArray2keyValue(json, keysList, valuesList);
		}
		keyValueBean.setKeys(keysList);
		keyValueBean.setValues(valuesList);
		return keyValueBean;
	}

	/**
	 * parseJsonArray2KeyValue
	 * @param json
	 * @param keysList
	 * @param valuesList
	 */
	private static void parseJsonArray2keyValue(JSON json, List<String> keysList, List<String> valuesList) {
		JSONArray jsonArray = (JSONArray) json;
		int size = jsonArray.size();
		for (int i = 0; i < size; i++) {
			KeyValueBean tempBean = json((JSON) jsonArray.get(i));
			if (tempBean != null) {
				if (tempBean.getKeys() != null && !tempBean.getKeys().isEmpty()) {
					keysList.addAll(tempBean.getKeys());
				}
				if (tempBean.getValues() != null && !tempBean.getValues().isEmpty()) {
					valuesList.addAll(tempBean.getValues());
				}
			}
		}
	}

	/**
	 * parse2KeyValue
	 * @param json
	 * @param keysList
	 * @param valuesList
	 */
	private static void parse2KeyValue(JSON json, List<String> keysList, List<String> valuesList) {
		JSONObject jsonObject = (JSONObject) json;
		Set<String> keySet = jsonObject.keySet();
		for (String key : keySet) {
			keysList.add(StringUtils.lowerCase(key));
			Object obj = jsonObject.get(key);
			if (obj != null) {
				if (obj instanceof JSONArray) {
					KeyValueBean tempBean = json((JSON) obj);
					if (tempBean != null) {
						if (tempBean.getKeys() != null && !tempBean.getKeys().isEmpty()) {
							keysList.addAll(tempBean.getKeys());
						}
						if (tempBean.getValues() != null && !tempBean.getValues().isEmpty()) {
							valuesList.addAll(tempBean.getValues());
						}
					}
				} else {
					valuesList.add(StringUtils.lowerCase(obj.toString()));
				}
			}
		}
	}
}

package com.confucian.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 * @author ice
 */
public class ValidatorUtil {

	/**
	 * 验证手机号
	 * @param mobile 手机号
	 * @return 手机号格式是否正确
	 */
	public static boolean validateMobile(String mobile) {
		String reges = "^((14[0-9])|(17[0-8])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
		Pattern pattern = Pattern.compile(reges);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.find();
	}

	/**
	 * 验证密码是否符合规则
	 * @param password 密码
	 * @return 密码是否符合规则
	 */
	public static boolean validatePassword(String password) {
		String reges = "^[0-9a-zA-Z]{6,16}$";
		Pattern pattern = Pattern.compile(reges);
		Matcher matcher = pattern.matcher(password);
		return matcher.find();
	}
}

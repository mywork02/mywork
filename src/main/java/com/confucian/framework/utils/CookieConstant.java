package com.confucian.framework.utils;

/**
 * cookie 相关常量
 * @author ice
 */
public final class CookieConstant {

	/**
	 * 客户端cookie参数名
	 */
	public static final String CHAT_USER_ID = "CHAT_USER_ID";

	/**
	 * 客户端cookie domain
	 */
	public static final String COOKIE_DOMAIN = "chatclient";

	/**
	 * 用户登录时的用户名
	 */
	public static final String USER_LOGIN_NAME = "uln_"; // 用户登录时的用户名

	/**
	 * cookie的有效时间7天
	 */
	public static final int TIME_SEVEN_DAY = 7 * 24 * 60 * 60;

	/**
	 * cookie的有效时间1年
	 */
	public static final int TIME_ONE_YEAR_DAY = 365 * 24 * 60 * 60;

	/**
	 * private constructor
	 */
	private CookieConstant() {
	}
}

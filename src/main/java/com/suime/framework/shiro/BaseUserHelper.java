package com.suime.framework.shiro;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户信息工具类
 *
 * @author ice
 */
public final class BaseUserHelper {

	/**
	 * 登录用户名login_name_userid_
	 */
	public static final String PREFIX_LOGIN_NAME_ADMINID = "login_name_adminid_";
	/**
	 * 登录用户名login_name_userid_
	 */
	public static final String PREFIX_LOGIN_NAME_USERID = "login_name_userid_";

	/**
	 * user token key
	 */
	public static final String USER_TOKEN = "USER_TOKEN";

	/**
	 * admin cookie key
	 */
	public static final String USER_ADMIN_TOKEN = "sui-wenku-admin-token";

	/**
	 * 参数中传递的token参数名
	 */
	public static final String PARAM_TOKEN = "token";

	/**
	 * admin token key
	 */
	public static final String ADMIN_TOKEN = "ADMIN_TOKEN";

	/**
	 * 用户缓存时间
	 */
	public static final Integer USER_CACHE_TIME = 60 * 60 * 24 * 15;

	/**
	 * currentUserId
	 */
	private static ThreadLocal<Long> userId = new ThreadLocal<Long>();

	/**
	 * currentSessionId
	 */
	private static ThreadLocal<String> sessionId = new ThreadLocal<String>();

	/**
	 * instance
	 */
	private static BaseUserHelper instance = new BaseUserHelper();

	/**
	 * constructor
	 */
	private BaseUserHelper() {
	}

	/**
	 * instance
	 *
	 * @return BaseUserHelper
	 */
	public static BaseUserHelper getInstance() {
		return instance;
	}

	/**
	 * 获取当前Subject
	 *
	 * @param userToken userToken
	 * @return subject
	 */
	public static Subject getCurrentSubject(final String userToken) {

		Subject subject = null;

		if (!StringUtils.isBlank(userToken)) {
			subject = new Subject.Builder().sessionCreationEnabled(true).sessionId(userToken).buildSubject();

		}
		if (subject == null) {
			subject = new Subject.Builder().authenticated(false).sessionCreationEnabled(true).buildSubject();

		}
		ThreadContext.bind(subject);
		return subject;
	}

	/**
	 * 初始化
	 *
	 * @param subject
	 */
	public void initAuthInfo(Subject subject) {
		if (subject != null && (subject.isAuthenticated() || subject.isRemembered())) {
			Serializable id = subject.getSession().getId();

			sessionId.set(id.toString());
		}
	}

	public static Long getCurrentUserId() {
		return userId.get();
	}

	public static String getSessionId() {
		return sessionId.get();
	}

	/**
	 * 根据token获取用户id
	 * @return 当前登录用户token
	 */
	public String getUserToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return getUserToken(request);
	}

	/**
	 * 根据token获取用户token
	 * @return 当前登录用户token
	 */
	private String getUserToken(HttpServletRequest request) {
		String userToken = request.getParameter(PARAM_TOKEN);
		if (StringUtils.isBlank(userToken)) {
			userToken = request.getHeader(PARAM_TOKEN);
		}
		if (StringUtils.isBlank(userToken)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(BaseUserHelper.USER_TOKEN)) {
						userToken = cookie.getValue();
						break;
					}
				}
			}
		}
		return userToken;
	}
}

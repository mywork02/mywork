package com.suime.library.shiro;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.confucian.framework.ioc.SpringContext;
import com.suime.common.cache.CacheService;
import com.suime.common.support.Configure;
import com.suime.library.shiro.support.TokenTypeEnum;

/**
 * 用户信息工具类
 *
 * @author ice
 */
public final class BaseUserHelper {

	/**
	 * 登录 token login_token_userid_
	 */
	// public static final String PREFIX_LOGIN_TOKEN_USERID = "login_token_userid_";
	public static final String PREFIX_LOGIN_TOKEN_USERID = Configure.getPropertyValue("ocs.token.prefix") + Configure.getPropertyValue("ocs.student.token.key");

	/**
	 * 管理员登陆key
	 */
	public static final String PREFIX_LOGIN_TOKEN_ADMINID = Configure.getPropertyValue("ocs.token.prefix") + Configure.getPropertyValue("ocs.admin.token.key");
	// "wenji:login_student_id?token=";

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
	 * 缓存服务
	 */
	// @Autowired
	private CacheService cacheService;

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

			Object userIdStr = getCacheService().get(PREFIX_LOGIN_TOKEN_USERID + id);
			if (userIdStr != null) {
				userId.set(Long.valueOf(userIdStr.toString()));
			}
			sessionId.set(id.toString());
		}
	}

	/**
	 * 获取cacheService及初始化
	 *
	 * @return cacheService
	 */
	private CacheService getCacheService() {
		if (cacheService == null) {
			cacheService = (CacheService) SpringContext.getBean("cacheService");
		}
		return cacheService;
	}

	public static Long getCurrentUserId() {
		return userId.get();
	}

	public static String getSessionId() {
		return sessionId.get();
	}

	/**
	 * 根据token获取用户id
	 *
	 * @return 当前登录用户id
	 */
	public Long getUserId() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userToken = getUserToken(request);
		Long currentUserId = null;
		if (StringUtils.isNotBlank(userToken)) {
			String userIdStr = (String) getCacheService().get(PREFIX_LOGIN_TOKEN_USERID + userToken);
			if (StringUtils.isNotBlank(userIdStr)) {
				String adminSuffix = TokenTypeEnum.ADMIN.getValue();
				if (StringUtils.endsWith(userIdStr, adminSuffix)) {
					userIdStr = userIdStr.replace(adminSuffix, "");
				}
				Logger logger = LoggerFactory.getLogger(getClass());
				try {
					currentUserId = Long.valueOf(userIdStr.toString());
					String path = request.getServletPath() + request.getPathInfo();
					logger.info(path + ":userToken:" + userToken);
					logger.info(path + ":currentUserId:" + currentUserId);
					if (currentUserId != null) {
						cacheService.set(BaseUserHelper.PREFIX_LOGIN_TOKEN_USERID + userToken, BaseUserHelper.USER_CACHE_TIME, currentUserId.toString());
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
		return currentUserId;
	}

	/**
	 * 根据token获取用户id
	 *
	 * @return 当前登录用户id
	 */
	public Long getAdminUserId() {
		String userToken = getUserToken();
		Long currentUserId = null;
		if (StringUtils.isNotBlank(userToken)) {
			String userIdStr = (String) getCacheService().get(PREFIX_LOGIN_TOKEN_ADMINID + userToken);
			if (StringUtils.isNotBlank(userIdStr)) {
				String adminSuffix = TokenTypeEnum.ADMIN.getValue();
				if (StringUtils.endsWith(userIdStr, adminSuffix)) {
					userIdStr = userIdStr.replace(adminSuffix, "");
				}
				Logger logger = LoggerFactory.getLogger(getClass());
				try {
					currentUserId = Long.valueOf(userIdStr.toString());
					if (currentUserId != null) {
						cacheService.set(BaseUserHelper.PREFIX_LOGIN_TOKEN_ADMINID + userToken, BaseUserHelper.USER_CACHE_TIME, currentUserId.toString());
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
		return currentUserId;
	}

	/**
	 * 根据token获取用户id
	 *
	 * @return 当前登录用户id
	 */
	public Long getAdminUserIdByCookie() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String userToken = "";
		if (StringUtils.isBlank(userToken)) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals(USER_ADMIN_TOKEN)) {
						userToken = cookie.getValue();
						break;
					}
				}
			}
		}
		Long currentUserId = null;
		if (StringUtils.isNotBlank(userToken)) {
			String userIdStr = (String) getCacheService().get(PREFIX_LOGIN_TOKEN_ADMINID + userToken);
			if (StringUtils.isNotBlank(userIdStr)) {
				String adminSuffix = TokenTypeEnum.ADMIN.getValue();
				if (StringUtils.endsWith(userIdStr, adminSuffix)) {
					userIdStr = userIdStr.replace(adminSuffix, "");
				}
				Logger logger = LoggerFactory.getLogger(getClass());
				try {
					currentUserId = Long.valueOf(userIdStr.toString());
					if (currentUserId != null) {
						cacheService.set(BaseUserHelper.PREFIX_LOGIN_TOKEN_ADMINID + userToken, BaseUserHelper.USER_CACHE_TIME, currentUserId.toString());
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
		return currentUserId;
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

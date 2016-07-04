package com.suime.library.helper;

import com.confucian.framework.ioc.SpringContext;
import com.suime.common.cache.CacheService;
import com.suime.common.dto.pack.UserBean;
import com.suime.common.error.BusinessErrors;
import com.suime.common.support.Configure;

/**
 * token相关处理方法
 * @author ice
 */
public final class TokenHelper {

	/**
	 * instance
	 */
	private static TokenHelper instance;

	/**
	 * cacheService
	 */
	private CacheService cacheService;

	/**
	 * constructor
	 */
	private TokenHelper() {
		cacheService = (CacheService) SpringContext.getBean("cacheService");
	}

	/**
	 * 根据token获取UserBean
	 * @param token token
	 * @return userbean
	 */
	public UserBean getUserBeanByToken(String token) {
		UserBean userBean = new UserBean();
		String studentId = (String) cacheService.get(Configure.getPropertyValue("student.token.prefix") + token);
		if (studentId != null) {
			userBean.setStudentFlag(true);
			userBean.setStudentId(studentId.toString());
		} else {
			userBean.setStudentFlag(false);
			String visitorId = (String) cacheService.get(Configure.getPropertyValue("visitor.token.prefix") + token);
			if (visitorId == null) {
				throw BusinessErrors.getInstance().tokenInvalidError();
			}
			userBean.setVisitorId(visitorId);
		}
		return userBean;
	}

	/**
	 * getInstance
	 * @return tokenHelper
	 */
	public static synchronized TokenHelper getInstance() {
		if (instance == null) {
			instance = new TokenHelper();
		}
		return instance;
	}
}

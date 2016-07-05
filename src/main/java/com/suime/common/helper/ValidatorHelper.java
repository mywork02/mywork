package com.suime.common.helper;

import org.apache.commons.lang.StringUtils;

import com.confucian.framework.ioc.SpringContext;
import com.suime.common.cache.CacheService;
import com.suime.common.support.Configure;

/**
 * 业务相关的验证
 * @author ice
 */
public final class ValidatorHelper {

	/**
	 * instance
	 */
	private static ValidatorHelper instance;

	/**
	 * cacheService
	 */
	private CacheService cacheService;

	/**
	 * constructor
	 */
	private ValidatorHelper() {

	}

	/**
	 * 检测验证码是否和缓存中最新保存的一致
	 * @param reason 发送验证码缘由
	 * @param cellphone 手机号
	 * @param passwordSent 客户端传回的验证码
	 * @return 验证码是否匹配
	 */
	public boolean checkVerificationCode(String reason, String cellphone, String passwordSent) {
		initCacheService();
		String key = Configure.getPropertyValue("cache.verification.code.prefix") + reason + cellphone;
		String verifyCode = (String) cacheService.get(key);
		if (StringUtils.equals(verifyCode, passwordSent)) {
			this.cacheService.delete(key);
			return true;
		}
		return false;
	}

	/**
	 * getInstance
	 * @return ValidatorHelper instance
	 */
	public static synchronized ValidatorHelper getInstance() {
		if (instance == null) {
			instance = new ValidatorHelper();
		}
		return instance;
	}

	/**
	 * 初始化cacheService
	 */
	private void initCacheService() {
		if (cacheService == null) {
			cacheService = (CacheService) SpringContext.getBean("cacheService");
		}
	}
}

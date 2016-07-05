package com.suime.common.error;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.web.exception.BusinessException;

/**
 * 短信验证错误
 * @author ice
 */
public class MobileValiditionException extends BusinessException {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -9215925544058529965L;

	/**
	 * constructor
	 * @param mobile 手机号
	 */
	public MobileValiditionException(String mobile) {
		super("01001", "errors mobile", SpringContext.getText("errors.mobile", mobile));
	}
}

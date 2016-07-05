package com.confucian.framework.web.exception;

import com.confucian.framework.dto.ErrorResultBean;

/**
 * 业务RuntieException
 * @author ice
 */
public class BusinessException extends RuntimeException {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -3752127912429021826L;

	/**
	 * 错误信息
	 */
	private ErrorResultBean errorResultBean;

	/**
	 * 错误信息中的参数
	 */
	private Object[] errorArgs = null;

	/**
	 * 构造函数
	 */
	public BusinessException() {
		super();
		this.errorResultBean = ExceptionUtils.generateErrorInfo(ErrorCode.UNKNOWN_ERROR);
	}

	/**
	 * 构造函数
	 * @param errorCode 错误编码
	 * @param error 错误 英文描述
	 * @param errorDescription 错误描述 中文描述
	 * @param errorArgs 错误填充信息
	 */
	public BusinessException(String errorCode, String error, String errorDescription, Object... errorArgs) {
		this.errorResultBean = ExceptionUtils.generateErrorInfo(errorCode, error, errorDescription);
		this.errorArgs = errorArgs;
	}

	public ErrorResultBean getErrorResultBean() {
		return errorResultBean;
	}

	public void setErrorResultBean(ErrorResultBean errorResultBean) {
		this.errorResultBean = errorResultBean;
	}

	public Object[] getErrorArgs() {
		return errorArgs;
	}

	public void setErrorArgs(Object[] errorArgs) {
		this.errorArgs = errorArgs;
	}

	@Override
	public String getMessage() {
		return this.errorResultBean.getErrorCode() + ":" + errorResultBean.getError();
	}

}

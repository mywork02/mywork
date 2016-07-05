package com.confucian.framework.dto;

/**
 * 通用出错Bean
 * @author ice
 */
public class ErrorResultBean {

	/**
	 * 错误信息
	 */
	private String error;
	/**
	 * 错误代码
	 */
	private String errorCode;
	/**
	 * 错误描述
	 */
	private String errorDescription;

	/**
	 * constructor
	 */
	public ErrorResultBean() {
	}

	/**
	 * coustructor
	 * @param error error
	 * @param errorCode errorCode
	 * @param errorDescription errorDescription
	 */
	public ErrorResultBean(String error, String errorCode, String errorDescription) {
		this.error = error;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

}

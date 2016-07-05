package com.suime.common.dto.pack;

/**
 * 通用bean
 * @author ice
 */
public class CommonBean {

	/**
	 * token
	 */
	private String token;

	/**
	 * 调用方法
	 */
	private String action;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}

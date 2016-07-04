package com.suime.library.shiro.support;

/**
 * Created by ice on 23/12/2015.
 */
public enum TokenTypeEnum {

	/**
	 * student
	 */
	STUDENT(""),
	/**
	 * 文库管理员
	 */
	ADMIN("-lbm");

	/**
	 * value
	 */
	private String value;

	/**
	 * constructor
	 * @param value
	 */
	TokenTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}

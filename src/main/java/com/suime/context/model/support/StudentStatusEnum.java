package com.suime.context.model.support;

import com.confucian.framework.ioc.SpringContext;

/**
 * Created by ice on 23/12/2015.
 */
public enum StudentStatusEnum {

	/**
	 * 正常用户
	 */
	NORMAL("nomal"),
	/**
	 * 文库黑名单
	 */
	WENKU_BLACK("wenku_black");

	/**
	 * code,i18n code
	 */
	private String code;

	/**
	 * i18n code 前缀
	 */
	private final String prefix = "student.status.";

	/**
	 * constructor
	 * @param code code
	 */
	StudentStatusEnum(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return SpringContext.getText(prefix + code);
	}
}

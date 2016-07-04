package com.suime.library.service.support;

/**
 * Created by ice on 19/11/2015.
 */
public enum OrderFieldEnum {
	/**
	 * 阅读数
	 */
	READ_COUNT("readCount", "read_count"),
	/**
	 * 评分
	 */
	RATINGS("ratings", "ratings"),
	/**
	 * 打印
	 */
	PRINT_COUNT("printCount", "print_count"),
	/**
	 * 收藏
	 */
	MARK_COUNT("markCount", "mark_count");

	/**
	 * 字段
	 */
	private String field;

	/**
	 * 列名
	 */
	private String dbField;

	/**
	 * constructor
	 * @param field
	 * @param dbField
	 */
	OrderFieldEnum(String field, String dbField) {
		this.field = field;
		this.dbField = dbField;
	}

	public String getField() {
		return field;
	}

	public String getDbField() {
		return dbField;
	}
}

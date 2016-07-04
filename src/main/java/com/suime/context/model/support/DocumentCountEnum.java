package com.suime.context.model.support;

/**
 * studentDocument 各种计数enum,如:阅读数,收藏数,评论数等
 *
 * @author ice
 */
public enum DocumentCountEnum {

	/**
	 * 评论数
	 */
	commentCount("comment_count"),
	/**
	 * 收藏数
	 */
	markCount("mark_count"),
	/**
	 * 打印数
	 */
	printCount("print_count"),
	/**
	 * 阅读数
	 */
	readCount("read_count"),
	/**
	 * 总评分次数
	 */
	ratingCount("rating_count");

	/**
	 * text
	 */
	private String text;

	/**
	 * constructor
	 * @param text text
	 */
	DocumentCountEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}

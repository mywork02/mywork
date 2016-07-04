package com.suime.context.model.support;

/**
 * Created by chenqy on 2016/3/23.
 * 消息类型,用于移动端区分
 */
public enum MessageTypeEnum {

	/**
	 * 消息类型 priview（文档）
	 */
	preview("preview"),
	/**
	 * 消息类型 comment（评论）
	 */
	comment("comment");

	/**
	 * text
	 */
	private String text;

	/**
	 * constructor
	 * @param s s
	 */
	MessageTypeEnum(String s) {
		this.text = s;
	}

	public String getText() {
		return text;
	}

}

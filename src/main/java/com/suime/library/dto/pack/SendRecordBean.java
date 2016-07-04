package com.suime.library.dto.pack;

/**
 * Created by chenqy on 2016/4/7.
 */
public class SendRecordBean {

	/**
	 * 发送的文档或文件夹id
	 */
	private Long id;

	/**
	 * 接收人id
	 */
	private Long receiverId;
	/**
	 * 发送人id
	 */
	private Long senderId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
}

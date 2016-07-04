package com.suime.library.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by chenqy on 2016/4/12.
 */
public class SendRecordDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 4716723441821937642L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	/**
	 * 文档名或文件夹名
	 */
	private String name;

	/**
	 * 类型，1：文件夹，0：文档
	 */
	private Byte type;

	/**
	 * 发送者昵称
	 */
	private String senderNickName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getSenderNickName() {
		return senderNickName;
	}

	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}
}

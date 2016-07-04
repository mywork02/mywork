package com.suime.context.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * wenku_doc_send_record 实体类
 * Created by chenqy 13/04/2016.
 */
public class SendRecord implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -5296852969506159725L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	/**
	 * 是否有效 1：有效，0：无效
	 */
	private Byte actived;

	/**
	 * 文档名或文件夹名
	 */
	private String name;

	/**
	 * 关联的文档或者文件夹id，如果拒收或未处理为null
	 */
	private Long associatedId;

	/**
	 * 关联的发送中间表id
	 */
	private Long sendCacheId;

	/**
	 * 接收者id
	 */
	private Long receiverId;

	/**
	 * 发送者id
	 */
	private Long senderId;

	/**
	 * 是否接收，1：接收，0：拒收，null：未处理
	 */
	private Byte isAccepted;

	/**
	 * 类型，1：文件夹，0：文档
	 */
	private Byte type;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	public Byte getActived() {
		return actived;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAssociatedId(Long associatedId) {
		this.associatedId = associatedId;
	}

	public Long getAssociatedId() {
		return associatedId;
	}

	public void setSendCacheId(Long sendCacheId) {
		this.sendCacheId = sendCacheId;
	}

	public Long getSendCacheId() {
		return sendCacheId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setIsAccepted(Byte isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Byte getIsAccepted() {
		return isAccepted;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getType() {
		return type;
	}

}
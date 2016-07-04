package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_listening 实体类
 * Created by ice 15/02/2016.
 */
public class Listening implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -6741263298684657029L;

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
	 * redirectUrl
	 */
	private String redirectUrl;

	/**
	 * remark
	 */
	private String remark;

	/**
	 * time
	 */
	private Long time;

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

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getTime() {
		return time;
	}

}
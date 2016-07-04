package com.suime.context.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * wenku_doc_send_cache 实体类
 * Created by chenqy 13/04/2016.
 */
public class SendCache implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -491802301554572196L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * data
	 */
	private String data;

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

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

}
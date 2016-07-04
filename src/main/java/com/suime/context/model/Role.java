package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_role 实体类
 * Created by ice 15/02/2016.
 */
public class Role implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 8013031833576515271L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * code
	 */
	private String code;

	/**
	 * description
	 */
	private String description;

	/**
	 * name
	 */
	private String name;

	/**
	 * status
	 */
	private Byte status;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getStatus() {
		return status;
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

}
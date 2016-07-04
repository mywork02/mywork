package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_black_list_record 实体类
 * Created by ice 15/02/2016.
 */
public class BlackListRecord implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -6857744038327010436L;

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
	 * adminId
	 */
	private Long adminId;

	/**
	 * operation
	 */
	private Byte operation;

	/**
	 * studentId
	 */
	private Long studentId;

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

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setOperation(Byte operation) {
		this.operation = operation;
	}

	public Byte getOperation() {
		return operation;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

}
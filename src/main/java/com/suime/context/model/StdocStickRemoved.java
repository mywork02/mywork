package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_stdoc_stick_removed 实体类
 * Created by ice 14/03/2016.
 */
public class StdocStickRemoved implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -854527528331045374L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 学生id
	 */
	private Long studentId;

	/**
	 * 文档id
	 */
	private Long studentDocumentId;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentDocumentId(Long studentDocumentId) {
		this.studentDocumentId = studentDocumentId;
	}

	public Long getStudentDocumentId() {
		return studentDocumentId;
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
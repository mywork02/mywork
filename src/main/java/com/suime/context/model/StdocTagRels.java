package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_stdoc_tag_rels 实体类
 * Created by ice 13/03/2016.
 */
public class StdocTagRels implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -1383123504729508921L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 文档上传人的id
	 */
	private Long studentId;

	/**
	 * 关联的文档
	 */
	private Long stdocId;

	/**
	 * 关联文档 tag
	 */
	private Long tagId;

	/**
	 * 关联度（权重）
	 */
	private Double relevancy;

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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setStdocId(Long stdocId) {
		this.stdocId = stdocId;
	}

	public Long getStdocId() {
		return stdocId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setRelevancy(Double relevancy) {
		this.relevancy = relevancy;
	}

	public Double getRelevancy() {
		return relevancy;
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
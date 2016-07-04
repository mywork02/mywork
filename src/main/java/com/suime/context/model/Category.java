package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_doc_category 实体类
 * Created by ice 15/02/2016.
 */
public class Category implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -7623307751888295994L;

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
	 * actived
	 */
	private Byte actived;

	/**
	 * code
	 */
	private String code;

	/**
	 * level
	 */
	private Byte level;

	/**
	 * name
	 */
	private String name;

	/**
	 * remarks
	 */
	private String remarks;

	/**
	 * majorGroupId
	 */
	private Long majorGroupId;

	/**
	 * parentId
	 */
	private Long parentId;

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

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public Byte getLevel() {
		return level;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setMajorGroupId(Long majorGroupId) {
		this.majorGroupId = majorGroupId;
	}

	public Long getMajorGroupId() {
		return majorGroupId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

}
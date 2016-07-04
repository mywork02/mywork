package com.suime.context.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * wenku_doc_directory 实体类
 * Created by chenqy 14/04/2016.
 */
public class Directory implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 8504058898631791366L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * name
	 */
	private String name;

	/**
	 * 简介
	 */
	private String intro;

	/**
	 * studentId
	 */
	private Long studentId;

	/**
	 * 权限,1:公开,3:私有
	 */
	private Byte permission;

	/**
	 * 文档来源,1:用户上传 2:接收的文档 3:接收的目录 4:用户创建的目录 9:公司上传文档
	 */
	private Byte source;

	/**
	 * parentId
	 */
	private Long parentId;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Byte actived;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return intro;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setPermission(Byte permission) {
		this.permission = permission;
	}

	public Byte getPermission() {
		return permission;
	}

	public void setSource(Byte source) {
		this.source = source;
	}

	public Byte getSource() {
		return source;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	public Byte getActived() {
		return actived;
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
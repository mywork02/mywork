package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_directory_content_rels 实体类
 * Created by ice 26/05/2016.
 */
public class DirectoryContentRels implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -3874215559125293656L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 文件夹所属学生id,冗余字段
	 */
	private Long studentId;

	/**
	 * 目录(文件夹)id
	 */
	private Long directoryId;

	/**
	 * 子目录(文件夹)或文档的关联id，和type结合使用
	 */
	private Long associatedId;

	/**
	 * 子目录(文件夹)或文档的名称
	 */
	private String associatedName;

	/**
	 * 文档后缀,前端显示可能需要
	 */
	private String extension;

	/**
	 * 类型type,1:文档，2:目录(文件夹)
	 */
	private Byte type;

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

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	public Long getDirectoryId() {
		return directoryId;
	}

	public void setAssociatedId(Long associatedId) {
		this.associatedId = associatedId;
	}

	public Long getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedName(String associatedName) {
		this.associatedName = associatedName;
	}

	public String getAssociatedName() {
		return associatedName;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getType() {
		return type;
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
package com.suime.context.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * wenku_doc_mark_record 实体类
 * Created by chenqy 20/04/2016.
 */
public class MarkRecord implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 430509947913196418L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 收藏夹id
	 */
	private Long favoriteId;

	/**
	 * studentId
	 */
	private Long studentId;

	/**
	 * 文档或文集的别名，默认为收藏时名称
	 */
	private String alias;

	/**
	 * studentNickName
	 */
	private String studentNickName;

	/**
	 * studentDocumentId
	 */
	@Deprecated
	private Long studentDocumentId;

	/**
	 * 关联的文档或文集id，和type结合使用
	 */
	private Long associatedId;

	/**
	 * 类型type,1:文档，2:文集
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

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setStudentNickName(String studentNickName) {
		this.studentNickName = studentNickName;
	}

	public String getStudentNickName() {
		return studentNickName;
	}

	@Deprecated
	public void setStudentDocumentId(Long studentDocumentId) {
		this.studentDocumentId = studentDocumentId;
	}

	@Deprecated
	public Long getStudentDocumentId() {
		return studentDocumentId;
	}

	public void setAssociatedId(Long associatedId) {
		this.associatedId = associatedId;
	}

	public Long getAssociatedId() {
		return associatedId;
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
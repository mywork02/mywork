package com.suime.library.dto.pack;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suime.context.model.MarkRecord;

/**
 * markRecordBean
 * @author ice
 */
public class MarkRecordBean {

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
	@JsonIgnore
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
	@JsonIgnore
	private Byte type;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Byte actived;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getStudentNickName() {
		return studentNickName;
	}

	public void setStudentNickName(String studentNickName) {
		this.studentNickName = studentNickName;
	}

	public Long getStudentDocumentId() {
		return studentDocumentId;
	}

	public void setStudentDocumentId(Long studentDocumentId) {
		this.studentDocumentId = studentDocumentId;
	}

	public Long getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(Long associatedId) {
		this.associatedId = associatedId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getActived() {
		return actived;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	/**
	 * transToMarkRecord
	 * @return markRecord
	 */
	public MarkRecord transToMarkRecord() {
		MarkRecord markRecord = new MarkRecord();
		markRecord.setActived(this.getActived());
		markRecord.setAlias(this.getAlias());
		markRecord.setType(this.getType());
		markRecord.setAssociatedId(this.getAssociatedId());
		markRecord.setFavoriteId(this.getFavoriteId());
		markRecord.setStudentDocumentId(this.getStudentDocumentId());
		markRecord.setStudentId(this.getStudentId());
		return markRecord;
	}
}

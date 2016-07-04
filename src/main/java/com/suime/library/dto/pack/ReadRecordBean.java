package com.suime.library.dto.pack;

import com.suime.context.model.ReadRecord;

import java.sql.Timestamp;

/**
 * markRecordBean
 * @author ice
 */
public class ReadRecordBean {

	/**
	 * id
	 */
	private Long id;

	/**
	 * 关联的studentDocumentId
	 */
	private Long studentDocumentId;

	/**
	 * 关联的student
	 */
	private Long studentId;

	/**
	 * 用户昵称,冗余字段,优化查询
	 */
	private String studentNickName;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentDocumentId() {
		return studentDocumentId;
	}

	public void setStudentDocumentId(Long studentDocumentId) {
		this.studentDocumentId = studentDocumentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentNickName() {
		return studentNickName;
	}

	public void setStudentNickName(String studentNickName) {
		this.studentNickName = studentNickName;
	}

	public Byte getActived() {
		return actived;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * transToReadRecord
	 * @return readRecord
	 */
	public ReadRecord transToReadRecord() {
		ReadRecord readRecord = new ReadRecord();
		readRecord.setActived(this.getActived());
		return readRecord;
	}
}

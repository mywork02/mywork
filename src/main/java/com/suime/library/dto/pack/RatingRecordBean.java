package com.suime.library.dto.pack;

import com.suime.context.model.RatingRecord;

import java.sql.Timestamp;

/**
 * markRecordBean
 * @author ice
 */
public class RatingRecordBean {

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
	 * 分数
	 */
	private Integer score;

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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
	 * transToRatingRecord
	 * @return ratingRecord
	 */
	public RatingRecord transToRatingRecord() {
		RatingRecord ratingRecord = new RatingRecord();
		ratingRecord.setActived(this.getActived());
		ratingRecord.setScore(this.getScore());
		return ratingRecord;
	}
}

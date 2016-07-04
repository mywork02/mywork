package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_doc_comment 实体类
 * Created by ice 15/02/2016.
 */
public class Comment implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -4426631042613874421L;

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
	 * studentNickName
	 */
	private String studentNickName;

	/**
	 * content
	 */
	private String content;

	/**
	 * replyStudentNickName
	 */
	private String replyStudentNickName;

	/**
	 * reviewState
	 */
	private Byte reviewState;

	/**
	 * studentId
	 */
	private Long studentId;

	/**
	 * studentDocumentId
	 */
	private Long studentDocumentId;

	/**
	 * parentId
	 */
	private Long parentId;

	/**
	 * replyId
	 */
	private Long replyId;

	/**
	 * replyStudentId
	 */
	private Long replyStudentId;

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

	public void setStudentNickName(String studentNickName) {
		this.studentNickName = studentNickName;
	}

	public String getStudentNickName() {
		return studentNickName;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setReplyStudentNickName(String replyStudentNickName) {
		this.replyStudentNickName = replyStudentNickName;
	}

	public String getReplyStudentNickName() {
		return replyStudentNickName;
	}

	public void setReviewState(Byte reviewState) {
		this.reviewState = reviewState;
	}

	public Byte getReviewState() {
		return reviewState;
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

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyStudentId(Long replyStudentId) {
		this.replyStudentId = replyStudentId;
	}

	public Long getReplyStudentId() {
		return replyStudentId;
	}

}
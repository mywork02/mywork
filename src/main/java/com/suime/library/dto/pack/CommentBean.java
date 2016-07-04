package com.suime.library.dto.pack;

import com.suime.context.model.Comment;

import java.sql.Timestamp;

/**
 * commentBean
 * @author ice
 */
public class CommentBean {

	/**
	 * 唯一id
	 */
	private Long id;

	/**
	 * 关联的studentDocument id
	 */
	private Long studentDocumentId;

	/**
	 * 关联的student id
	 */
	private Long studentId;

	/**
	 * 用户昵称,冗余字段,优化查询
	 */
	private String studentNickName;

	/**
	 * 评论内容
	 */
	private String content;

	/**
	 * 被回复的评论id
	 */
	private Long replyId;

	/**
	 * 被回复的评论 用户 昵称,冗余字段,优化查询
	 */
	private String replyStudent;

	/**
	 * 被回复的评论 用户 昵称,冗余字段,优化查询
	 */
	private String replyNickName;

	/**
	 * 父评论id
	 */
	private Long parentId;
	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Byte actived;

	/**
	 * 审核状态,1:待审核状态,2:通过状态.预留字段,先默认通过状态
	 */
	private Byte reviewState;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	/**
	 * trans to docComment
	 * @return docComment
	 */
	public Comment transToDocComment() {
		Comment docComment = new Comment();
		docComment.setActived(this.getActived());
		docComment.setContent(this.getContent());
		docComment.setCreatedAt(this.getCreatedAt());
		docComment.setUpdatedAt(this.getUpdatedAt());
		docComment.setReviewState(this.getReviewState());
		return docComment;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public String getReplyStudent() {
		return replyStudent;
	}

	public void setReplyStudent(String replyStudent) {
		this.replyStudent = replyStudent;
	}

	public String getReplyNickName() {
		return replyNickName;
	}

	public void setReplyNickName(String replyNickName) {
		this.replyNickName = replyNickName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Byte getActived() {
		return actived;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	public Byte getReviewState() {
		return reviewState;
	}

	public void setReviewState(Byte reviewState) {
		this.reviewState = reviewState;
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
}

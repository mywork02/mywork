package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_doc_read_record 实体类
 * Created by ice 15/02/2016.
 */
public class ReadRecord implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 3300920336848570934L;

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
	 * studentId
	 */
	private Long studentId;

	/**
	 * studentDocumentId
	 */
	private Long studentDocumentId;
	//
	// /**
	// * count
	// */
	// private Integer count;

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

	// public void setCount(Integer count){
	// this.count=count;
	// }
	//
	// public Integer getCount(){
	// return count;
	// }

}
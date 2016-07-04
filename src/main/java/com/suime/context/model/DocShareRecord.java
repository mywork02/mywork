package com.suime.context.model;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_doc_share_record 实体类
 * Created by ice 21/06/2016.
 */ 
public class DocShareRecord implements Serializable {
	/**
	 * id
	 */
	private Long id;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
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

	public void setId(Long id){
	this.id = id;
	}

	public Long getId(){
		return id;
	}

	public void setCreatedAt(Timestamp createdAt){
	this.createdAt = createdAt;
	}

	public Timestamp getCreatedAt(){
		return createdAt;
	}

	public void setUpdatedAt(Timestamp updatedAt){
	this.updatedAt = updatedAt;
	}

	public Timestamp getUpdatedAt(){
		return updatedAt;
	}

	public void setActived(Byte actived){
	this.actived = actived;
	}

	public Byte getActived(){
		return actived;
	}

	public void setStudentNickName(String studentNickName){
	this.studentNickName = studentNickName;
	}

	public String getStudentNickName(){
		return studentNickName;
	}

	public void setStudentId(Long studentId){
	this.studentId = studentId;
	}

	public Long getStudentId(){
		return studentId;
	}

	public void setStudentDocumentId(Long studentDocumentId){
	this.studentDocumentId = studentDocumentId;
	}

	public Long getStudentDocumentId(){
		return studentDocumentId;
	}

}
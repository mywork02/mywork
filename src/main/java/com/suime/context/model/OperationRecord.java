package com.suime.context.model;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_doc_operation_record 实体类
 * Created by ice 30/05/2016.
 */ 
public class OperationRecord implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 6898006382455312172L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 学生id
	 */
	private Long studentId;

	/**
	 * 关联的id
	 */
	private Long associatedId;

	/**
	 * 对应的文档id
	 */
	private Long studentDocumentId;

	/**
	 * 数据类型，1:文档(暂时只有文档)
	 */
	private Byte dataType;

	/**
	 * 操作类型，1：上传，2：修改，3：公开，4：阅读，5：收藏
	 */
	private Byte operateType;

	/**
	 * 当操作类型为“修改”时，保存修改前的信息
	 */
	private String data;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Byte actived;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	public void setId(Long id){
	this.id = id;
	}

	public Long getId(){
		return id;
	}

	public void setStudentId(Long studentId){
	this.studentId = studentId;
	}

	public Long getStudentId(){
		return studentId;
	}

	public void setAssociatedId(Long associatedId){
	this.associatedId = associatedId;
	}

	public Long getAssociatedId(){
		return associatedId;
	}

	public void setStudentDocumentId(Long studentDocumentId){
	this.studentDocumentId = studentDocumentId;
	}

	public Long getStudentDocumentId(){
		return studentDocumentId;
	}

	public void setDataType(Byte dataType){
	this.dataType = dataType;
	}

	public Byte getDataType(){
		return dataType;
	}

	public void setOperateType(Byte operateType){
	this.operateType = operateType;
	}

	public Byte getOperateType(){
		return operateType;
	}

	public void setData(String data){
	this.data = data;
	}

	public String getData(){
		return data;
	}

	public void setActived(Byte actived){
	this.actived = actived;
	}

	public Byte getActived(){
		return actived;
	}

	public void setCreatedAt(Timestamp createdAt){
	this.createdAt = createdAt;
	}

	public Timestamp getCreatedAt(){
		return createdAt;
	}

}
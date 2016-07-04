package com.suime.library.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * printRecordDto
 *
 * @author ice
 */
public class PrintRecordDto implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = -4090177146674384176L;

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

    /**
     * constructor
     */
    public PrintRecordDto() {
    }

//	/**
//	 * constructor
//	 * @param printRecord
//	 */
//	public PrintRecordDto(PrintRecord printRecord) {
//		if (printRecord != null) {
//			this.setId(printRecord.getId());
//			this.setActived(printRecord.getActived());
//			this.setCreatedAt(printRecord.getCreatedAt());
//			StudentDocument studentDocument = printRecord.getStudentDocument();
//			if (studentDocument != null) {
//				this.setStudentDocumentId(studentDocument.getId());
//			}
//			Student student = printRecord.getStudent();
//			if (student != null) {
//				this.setStudentId(student.getId());
//				this.setStudentNickName(printRecord.getStudentNickName());
//			}
//			this.setUpdatedAt(printRecord.getUpdatedAt());
//		}
//	}

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

}

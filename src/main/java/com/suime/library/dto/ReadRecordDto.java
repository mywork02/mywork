package com.suime.library.dto;

import com.suime.context.model.ReadRecord;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * documentDto
 *
 * @author ice
 */
public class ReadRecordDto implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = -1354786954566680088L;

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
     * 学生id
     */
    private Long studentId;

    /**
     * 文档id
     */
    private Long studentDocumentId;

    /**
     * 学生昵称
     */
    private String studentNickName;

    /**
     * default constructor
     */
    public ReadRecordDto() {
        // TODO
    }

    /**
     * constructor
     *
     * @param readRecord
     */
    public ReadRecordDto(ReadRecord readRecord) {
        if (readRecord != null) {
            this.id = readRecord.getId();
            this.createdAt = readRecord.getCreatedAt();
            this.updatedAt = readRecord.getUpdatedAt();
            this.actived = readRecord.getActived();
            this.studentId = readRecord.getStudentId();
            this.studentDocumentId = readRecord.getStudentDocumentId();
            this.studentNickName = readRecord.getStudentNickName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Byte getActived() {
        return actived;
    }

    public void setActived(Byte actived) {
        this.actived = actived;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentDocumentId() {
        return studentDocumentId;
    }

    public void setStudentDocumentId(Long studentDocumentId) {
        this.studentDocumentId = studentDocumentId;
    }

    public String getStudentNickName() {
        return studentNickName;
    }

    public void setStudentNickName(String studentNickName) {
        this.studentNickName = studentNickName;
    }

}

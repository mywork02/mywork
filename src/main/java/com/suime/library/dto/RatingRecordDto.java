package com.suime.library.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.suime.context.model.RatingRecord;

/**
 * ratingRecordDto
 *
 * @author ice
 */
public class RatingRecordDto implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = -2133719785966706888L;
    
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

    /**
     * constructor
     */
    public RatingRecordDto() {
    }

    /**
     * constructor
     *
     * @param ratingRecord
     */
    public RatingRecordDto(RatingRecord ratingRecord) {
        if (ratingRecord != null) {
            this.setId(ratingRecord.getId());
            this.setActived(ratingRecord.getActived());
            this.setCreatedAt(ratingRecord.getCreatedAt());
            this.setStudentDocumentId(ratingRecord.getStudentDocumentId());
            this.setStudentId(ratingRecord.getStudentId());
            this.setStudentNickName(ratingRecord.getStudentNickName());
            this.setScore(ratingRecord.getScore());
            this.setUpdatedAt(ratingRecord.getUpdatedAt());
        }
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

}

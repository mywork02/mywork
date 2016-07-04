package com.suime.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suime.context.model.MarkRecord;

import java.sql.Timestamp;

/**
 * markRecordDto
 *
 * @author ice
 */
public class MarkRecordDto {

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
     * 目录别名
     */
    private String alias;

    /**
     * 关联的目录或文档
     */
    private Long associatedId;

    /**
     * 是否目录
     */
    private Byte isDirectory;


    /**
     * 获得的积分
     */
    private PointDto point;
    
    /**
	 * 文档后缀
	 */
	private String suffix;
	
	
	/**
	 * 收藏夹ID
	 */
	private String favoriteId;


    /**
     * constructor
     */
    public MarkRecordDto() {
    }

    /**
     * constructor
     *
     * @param markRecord
     */
    public MarkRecordDto(MarkRecord markRecord) {
        if (markRecord != null) {
            this.setId(markRecord.getId());
            this.setActived(markRecord.getActived());
            this.setCreatedAt(markRecord.getCreatedAt());
            this.setStudentDocumentId(markRecord.getStudentDocumentId());
            this.setStudentId(markRecord.getStudentId());
            this.setStudentNickName(markRecord.getStudentNickName());
            this.setUpdatedAt(markRecord.getUpdatedAt());
        }
    }
    
    
    

	public String getFavoriteId() {
		return favoriteId;
	}

	public void setFavoriteId(String favoriteId) {
		this.favoriteId = favoriteId;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(Long associatedId) {
        this.associatedId = associatedId;
    }

    public Byte getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(Byte isDirectory) {
        this.isDirectory = isDirectory;
    }

    @JsonIgnore
    public PointDto getPoint() {
        return point;
    }

    public void setPoint(PointDto point) {
        this.point = point;
    }
}

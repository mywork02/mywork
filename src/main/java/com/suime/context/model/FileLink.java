package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_filelink 实体类
 * Created by ice 15/02/2016.
 */
public class FileLink implements Serializable {

    /**
	 * sid
	 */
	private static final long serialVersionUID = 7279981427348024570L;

	/**
     * id
     */
    private Long id;

    /**
     * studentId
     */
    private Long studentId;

    /**
     * visitorId
     */
    private String visitorId;

    /**
     * name
     */
    private String name;

    /**
     * fileId
     */
    private Long fileId;

    /**
     * createdAt
     */
    private Timestamp createdAt;

    /**
     * updatedAt
     */
    private Timestamp updatedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getFileId() {
        return fileId;
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

}
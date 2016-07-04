package com.suime.library.dto;

import java.io.Serializable;

/**
 * documentDto
 *
 * @author ice
 */
public class DocumentDto implements Serializable {

    /**
     * sid
     */
    private static final long serialVersionUID = -2345275019527785196L;

    /**
     * id
     */
    private Long id;

    /**
     * 文档名称(默认文件名不带后缀,和文件名无必然关系)
     */
    private String name;

    /**
     * 文档标签,以 "," 隔开
     */
    private String tags;

    /**
     * fileId
     */
    private Long fileId;

    /**
     * fileKey
     */
    private String fileKey;

    /**
     * 文件名称(带有后缀)
     */
    private String fileName;

    /**
     * 文件后缀名,不带.号
     */
    private String extension;

    /**
     * 文件长度
     */
    private Long fileLength;

    /**
     * 文件页数，若是 PPT 则是 Slide 数，若为 NULL 则意为未计算。
     */
    private Integer pageCount;

    /**
     * 文档简介
     */
    private String intro;

    /**
     * 学校id
     */
    private Long schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 专业id
     */
    private Long majorGroupId;

    /**
     * 专业名称
     */
    private String majorGroupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getFileLength() {
        return fileLength;
    }

    public void setFileLength(Long fileLength) {
        this.fileLength = fileLength;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Long getMajorGroupId() {
        return majorGroupId;
    }

    public void setMajorGroupId(Long majorGroupId) {
        this.majorGroupId = majorGroupId;
    }

    public String getMajorGroupName() {
        return majorGroupName;
    }

    public void setMajorGroupName(String majorGroupName) {
        this.majorGroupName = majorGroupName;
    }
}

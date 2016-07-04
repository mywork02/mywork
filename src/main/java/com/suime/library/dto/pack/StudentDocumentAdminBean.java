/**
 *StudentDocumentAdminBean.java
 * 2015年12月8日
 */
package com.suime.library.dto.pack;

import java.sql.Timestamp;

/**
 * StudentDocumentAdminBean
 * 管理员上传文档使用
 * 
 * @author origin
 *
 */
public class StudentDocumentAdminBean {

	/**
     * id
     */
    private Long id;

    /**
     * 所属学生id
     */
    private Long studentId;

    /**
     * 学生昵称
     */
    private String studentNickName;

    /**
     * 一级分类
     */
    private String levelOne;
    
    /**
     * 二级分类
     */
    private String levelTwo;
    
    /**
     * 三级分类
     */
    private String levelThree;
    
    /**
     * 四级分类
     */
    private String levelFour;
    
    /**
     * 五级分类
     */
    private String levelFive;
    
    /**
     * 关联文档id
     */
    private Long documentId;

    /**
     * 关联文件id
     */
    private Long fileId;

    /**
     * 文件名称(带有后缀)
     */
    private String fileName;

    /**
     * 文档名称(默认文件名不带后缀,和文件名无必然关系)
     */
    private String name;

    /**
     * 文档标签,以 "," 隔开
     */
    private String tags;

    /**
     * 文档简介
     */
    private String intro;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 专业,部分分类是有效 id
     */
    private Long majorGroupId;

    /**
     * 文档价格
     */
    private Integer price;

    /**
     * 审核状态,1:待审核,2:审核通过
     */
    private Byte reviewState;

    /**
     * 权限,
     */
    private Byte permission;

    /**
     * 是否可预览,0为不可预览,1:为可预览
     */
    private Byte preview;

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
     * 是否被推荐的,0:不被推荐,1:被推荐.默认为0
     */
    private Byte recommended;

    /**
     * 文档来源,1:用户上传,2:公司上传.默认为1
     */
    private Byte source;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getDocumentId() {
		return documentId;
	}
	
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Byte getReviewState() {
		return reviewState;
	}

	public void setReviewState(Byte reviewState) {
		this.reviewState = reviewState;
	}

	public Byte getPermission() {
		return permission;
	}

	public void setPermission(Byte permission) {
		this.permission = permission;
	}

	public Byte getPreview() {
		return preview;
	}

	public void setPreview(Byte preview) {
		this.preview = preview;
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

	public Byte getRecommended() {
		return recommended;
	}

	public void setRecommended(Byte recommended) {
		this.recommended = recommended;
	}

	public Byte getSource() {
		return source;
	}

	public void setSource(Byte source) {
		this.source = source;
	}

	public String getLevelOne() {
		return levelOne;
	}

	public void setLevelOne(String levelOne) {
		this.levelOne = levelOne;
	}

	public String getLevelTwo() {
		return levelTwo;
	}

	public void setLevelTwo(String levelTwo) {
		this.levelTwo = levelTwo;
	}

	public String getLevelThree() {
		return levelThree;
	}

	public void setLevelThree(String levelThree) {
		this.levelThree = levelThree;
	}

	public String getLevelFour() {
		return levelFour;
	}

	public void setLevelFour(String levelFour) {
		this.levelFour = levelFour;
	}

	public String getLevelFive() {
		return levelFive;
	}

	public void setLevelFive(String levelFive) {
		this.levelFive = levelFive;
	}
	
}

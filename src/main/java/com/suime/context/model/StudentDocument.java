package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_student_document 实体类
 * Created by ice 15/02/2016.
 */
public class StudentDocument implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -1994368903609330614L;

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
	 * categoryCode
	 */
	private String categoryCode;

	/**
	 * fileKey
	 */
	private String fileKey;

	/**
	 * 文件名称(带有后缀)
	 */
	private String fileName;

	/**
	 * 文档简介
	 */
	private String intro;

	/**
	 * 文档名称(默认文件名不带后缀,和文件名无必然关系)
	 */
	private String name;

	/**
	 * tags
	 */
	private String tags;

	/**
	 * commentCount
	 */
	private Integer commentCount;

	/**
	 * markCount
	 */
	private Integer markCount;

	/**
	 * 文档页数
	 */
	private Integer pageCount;

	/**
	 * 权限,1:公开,3:私有
	 */
	private Byte permission;

	/**
	 * 是否可预览,0为不可预览,1:为可预览
	 */
	private Byte preview;

	/**
	 * price
	 */
	private Integer price;

	/**
	 * printCount
	 */
	private Integer printCount;

	/**
	 * ratingCount
	 */
	private Integer ratingCount;

	/**
	 * ratings
	 */
	private Integer ratings;

	/**
	 * readCount
	 */
	private Integer readCount;

	/**
	 * 审核状态,1:待审核,2:审核通过
	 */
	private Byte reviewState;

	/**
	 * shareCount
	 */
	private Integer shareCount;

	/**
	 * studentId
	 */
	private Long studentId;

	/**
	 * 学生昵称
	 */
	private String studentNickName;

	/**
	 * categoryId
	 */
	private Long categoryId;

	/**
	 * majorGroupId
	 */
	private Long majorGroupId;

	/**
	 * schoolId
	 */
	private Long schoolId;

	/**
	 * documentId
	 */
	private Long documentId;

	/**
	 * fileId
	 */
	private Long fileId;

	/**
	 * 是否被推荐的,0:不被推荐,1:被推荐.默认为0
	 */
	private Byte recommended;

	/**
	 * 文档来源,1:用户上传 2:接收的文档 3:接收的目录 4:用户创建的目录 9:公司上传文档;11:拍照转pdf的文档
	 */
	private Byte source;

	/**
	 * directoryId
	 */
	private Long directoryId;

	/**
	 * 缩略图标志，默认为0，1:有缩略图，0：无缩略图
	 */
	private Integer thumbnail;

	/**
	 * 类型 1：普通文档 2：精品文档(推荐文档),默认为1,用于用户(楼长)个人管理字段
	 */
	private Byte type;

	/**
	 * 置顶:0:非置顶文档,1:置顶文档,默认为0,公司给每个人添加的推荐置顶文档
	 */
	private Byte stick;

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

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return intro;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTags() {
		return tags;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setMarkCount(Integer markCount) {
		this.markCount = markCount;
	}

	public Integer getMarkCount() {
		return markCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPermission(Byte permission) {
		this.permission = permission;
	}

	public Byte getPermission() {
		return permission;
	}

	public void setPreview(Byte preview) {
		this.preview = preview;
	}

	public Byte getPreview() {
		return preview;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	public Integer getPrintCount() {
		return printCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}

	public Integer getRatings() {
		return ratings;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReviewState(Byte reviewState) {
		this.reviewState = reviewState;
	}

	public Byte getReviewState() {
		return reviewState;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentNickName(String studentNickName) {
		this.studentNickName = studentNickName;
	}

	public String getStudentNickName() {
		return studentNickName;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setMajorGroupId(Long majorGroupId) {
		this.majorGroupId = majorGroupId;
	}

	public Long getMajorGroupId() {
		return majorGroupId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setRecommended(Byte recommended) {
		this.recommended = recommended;
	}

	public Byte getRecommended() {
		return recommended;
	}

	public void setSource(Byte source) {
		this.source = source;
	}

	public Byte getSource() {
		return source;
	}

	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	public Long getDirectoryId() {
		return directoryId;
	}

	public void setThumbnail(Integer thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Integer getThumbnail() {
		return thumbnail;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getType() {
		return type;
	}

	public void setStick(Byte stick) {
		this.stick = stick;
	}

	public Byte getStick() {
		return stick;
	}
}
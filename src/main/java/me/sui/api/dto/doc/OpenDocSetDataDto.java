/**
 * 
 */
package me.sui.api.dto.doc;

import java.io.Serializable;
import java.util.Date;

/**
 * @author davidclj
 *
 */
public class OpenDocSetDataDto implements Serializable {

	/**
	 * 关联表ID
	 */
	private Long id;

	/**
	 * 文档类型
	 */
	private Integer docType;

	/**
	 * 文件/文件夹ID
	 */
	private Long docId;

	/**
	 * 创建时间
	 */
	private Date createdAt;

	/**
	 * 更新时间
	 */
	private Date updatedAt;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Integer actived;

	/**
	 * 
	 */
	private String categoryCode;

	/**
	 * 
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
	 * 
	 */
	private String tags;

	/**
	 * 
	 */
	private String keywords;

	/**
	 * 
	 */
	private Integer commentCount;

	/**
	 * 
	 */
	private Integer markCount;

	/**
	 * 文档页数
	 */
	private Integer pageCount;

	/**
	 * 权限,1:公开,3:私有
	 */
	private Integer permission;

	/**
	 * 是否可预览,0为不可预览,1:为可预览
	 */
	private Integer preview;

	/**
	 * 
	 */
	private Integer price;

	/**
	 * 
	 */
	private Integer printCount;

	/**
	 * 
	 */
	private Integer ratingCount;

	/**
	 * 
	 */
	private Integer ratings;

	/**
	 * 
	 */
	private Integer readCount;

	/**
	 * 审核状态,1:待审核,2:审核通过
	 */
	private Integer reviewState;

	/**
	 * 
	 */
	private Integer shareCount;

	/**
	 * 
	 */
	private Long studentId;

	/**
	 * 学生昵称
	 */
	private String studentNickName;

	/**
	 * 
	 */
	private Long categoryId;

	/**
	 * 
	 */
	private Long majorGroupId;

	/**
	 * 
	 */
	private Long schoolId;

	/**
	 * 
	 */
	private Long documentId;

	/**
	 * 
	 */
	private Long fileId;

	/**
	 * 是否被推荐的,0:不被推荐,1:被推荐.默认为0
	 */
	private Integer recommended;

	/**
	 * 文档来源,1:用户上传 2:接收的文档 3:接收的目录 4:用户创建的目录 9:公司上传文档
	 */
	private Integer source;

	/**
	 * 缩略图标志，默认为0;0:无缩略图;1:有缩略图
	 */
	private Integer thumbnail;

	/**
	 * directoryId
	 */
	private Long directoryId;

	/**
	 * 类型 1：普通文档 2：精品文档(推荐文档),默认为1,用于用户(楼长)个人管理字段
	 */
	private Integer type;

	/**
	 * 置顶:0:非置顶文档,1:置顶文档,默认为0,公司给每个人添加的推荐置顶文档
	 */
	private Integer stick;

	/**
	 * 
	 */
	private Integer tagCalced;

	/**
	 * @see #docType
	 */
	public Integer getDocType() {
		return docType;
	}

	/**
	 * @see #docType
	 */
	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	/**
	 * @see #docId
	 */
	public Long getDocId() {
		return docId;
	}

	/**
	 * @see #docId
	 */
	public void setDocId(Long docId) {
		this.docId = docId;
	}

	/**
	 * @see #createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @see #createdAt
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @see #updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @see #updatedAt
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @see #actived
	 */
	public Integer getActived() {
		return actived;
	}

	/**
	 * @see #actived
	 */
	public void setActived(Integer actived) {
		this.actived = actived;
	}

	/**
	 * @see #categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @see #categoryCode
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @see #fileKey
	 */
	public String getFileKey() {
		return fileKey;
	}

	/**
	 * @see #fileKey
	 */
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	/**
	 * @see #fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @see #fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @see #intro
	 */
	public String getIntro() {
		return intro;
	}

	/**
	 * @see #intro
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @see #name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see #name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @see #tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @see #tags
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @see #keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @see #keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @see #commentCount
	 */
	public Integer getCommentCount() {
		return commentCount;
	}

	/**
	 * @see #commentCount
	 */
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * @see #markCount
	 */
	public Integer getMarkCount() {
		return markCount;
	}

	/**
	 * @see #markCount
	 */
	public void setMarkCount(Integer markCount) {
		this.markCount = markCount;
	}

	/**
	 * @see #pageCount
	 */
	public Integer getPageCount() {
		return pageCount;
	}

	/**
	 * @see #pageCount
	 */
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * @see #permission
	 */
	public Integer getPermission() {
		return permission;
	}

	/**
	 * @see #permission
	 */
	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	/**
	 * @see #preview
	 */
	public Integer getPreview() {
		return preview;
	}

	/**
	 * @see #preview
	 */
	public void setPreview(Integer preview) {
		this.preview = preview;
	}

	/**
	 * @see #price
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * @see #price
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * @see #printCount
	 */
	public Integer getPrintCount() {
		return printCount;
	}

	/**
	 * @see #printCount
	 */
	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	/**
	 * @see #ratingCount
	 */
	public Integer getRatingCount() {
		return ratingCount;
	}

	/**
	 * @see #ratingCount
	 */
	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	/**
	 * @see #ratings
	 */
	public Integer getRatings() {
		return ratings;
	}

	/**
	 * @see #ratings
	 */
	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}

	/**
	 * @see #readCount
	 */
	public Integer getReadCount() {
		return readCount;
	}

	/**
	 * @see #readCount
	 */
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	/**
	 * @see #reviewState
	 */
	public Integer getReviewState() {
		return reviewState;
	}

	/**
	 * @see #reviewState
	 */
	public void setReviewState(Integer reviewState) {
		this.reviewState = reviewState;
	}

	/**
	 * @see #shareCount
	 */
	public Integer getShareCount() {
		return shareCount;
	}

	/**
	 * @see #shareCount
	 */
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	/**
	 * @see #studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @see #studentId
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @see #studentNickName
	 */
	public String getStudentNickName() {
		return studentNickName;
	}

	/**
	 * @see #studentNickName
	 */
	public void setStudentNickName(String studentNickName) {
		this.studentNickName = studentNickName;
	}

	/**
	 * @see #categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @see #categoryId
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @see #majorGroupId
	 */
	public Long getMajorGroupId() {
		return majorGroupId;
	}

	/**
	 * @see #majorGroupId
	 */
	public void setMajorGroupId(Long majorGroupId) {
		this.majorGroupId = majorGroupId;
	}

	/**
	 * @see #schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}

	/**
	 * @see #schoolId
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * @see #documentId
	 */
	public Long getDocumentId() {
		return documentId;
	}

	/**
	 * @see #documentId
	 */
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	/**
	 * @see #fileId
	 */
	public Long getFileId() {
		return fileId;
	}

	/**
	 * @see #fileId
	 */
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	/**
	 * @see #recommended
	 */
	public Integer getRecommended() {
		return recommended;
	}

	/**
	 * @see #recommended
	 */
	public void setRecommended(Integer recommended) {
		this.recommended = recommended;
	}

	/**
	 * @see #source
	 */
	public Integer getSource() {
		return source;
	}

	/**
	 * @see #source
	 */
	public void setSource(Integer source) {
		this.source = source;
	}

	/**
	 * @see #thumbnail
	 */
	public Integer getThumbnail() {
		return thumbnail;
	}

	/**
	 * @see #thumbnail
	 */
	public void setThumbnail(Integer thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @see #directoryId
	 */
	public Long getDirectoryId() {
		return directoryId;
	}

	/**
	 * @see #directoryId
	 */
	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	/**
	 * @see #type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @see #type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @see #stick
	 */
	public Integer getStick() {
		return stick;
	}

	/**
	 * @see #stick
	 */
	public void setStick(Integer stick) {
		this.stick = stick;
	}

	/**
	 * @see #tagCalced
	 */
	public Integer getTagCalced() {
		return tagCalced;
	}

	/**
	 * @see #tagCalced
	 */
	public void setTagCalced(Integer tagCalced) {
		this.tagCalced = tagCalced;
	}

	/**
	 * @see #id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @see #id
	 */
	public void setId(Long id) {
		this.id = id;
	}

}

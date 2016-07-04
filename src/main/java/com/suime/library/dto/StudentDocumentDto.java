package com.suime.library.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suime.common.helper.ThumbnailAliyunOSSHelper;
import com.suime.common.util.FileTypeUtil;
import com.suime.context.model.StudentDocument;
import com.suime.library.helper.StdocDtoHelper;

/**
 * studentDocumentDto
 *
 * @author ice
 */
public class StudentDocumentDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -4294051377238159057L;
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
	 * 文档分类
	 */
	private Long categoryId;

	/**
	 * 文档分类编码,用来根据分类模糊查询
	 */
	private String categoryCode;

	/**
	 * 文档分类名称
	 */
	private String categoryName;

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
	 * 学校,分类为专业资料等时有效id
	 */
	private Long schoolId;

	/**
	 * 学校,分类为专业资料等时有效 name
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
	 * 文档页数
	 */
	private Integer pageCount;

	/**
	 * 评论数
	 */
	private Integer commentCount;

	/**
	 * 收藏数
	 */
	private Integer markCount;

	/**
	 * 打印数
	 */
	private Integer printCount;

	/**
	 * 阅读数
	 */
	private Integer readCount;

	/**
	 * 总评分
	 */
	private Integer ratings;

	/**
	 * 总评分次数
	 */
	private Integer ratingCount;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Byte actived;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 文档创建时间
	 */
	private Timestamp documentCreatedAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	/**
	 * fileKey
	 */
	private String fileKey;

	/**
	 * 缩略图标志，默认为0，1:有缩略图，0：无缩略图
	 */
	private Integer thumbnail;

	/**
	 * 缩略图 url
	 */
	private String thumbnailUrl;

	/**
	 * 收藏id
	 */
	private Long markRecordId;

	/**
	 * 评分id
	 */
	private Long ratingRecordId;

	/**
	 * 个人评分
	 */
	private Integer personalScore;

	/**
	 * 权重
	 */
	@SuppressWarnings("unused")
	private Integer weight;

	/**
	 * 发送者id，当此文档为接收文档时
	 */
	private Long senderId;

	/**
	 * 发送者昵称，当此文档为接收文档时
	 */
	private String senderNickName;
	
	/**
	 * 发送者头像，当此文档为接收文档时
	 */
	private String avatar;
	
	/**
	 * 发送者简介，当此文档为接收文档时
	 */
	private String studentIntro;

	/**
	 * 获得的积分
	 */
	private Integer point;
	/**
	 * 积分备注
	 */
	private String pointMemo;

	/**
	 * 上次阅读时间 
	 */
	private Timestamp pastReadedAt;

	/**
	 * 上次阅读时间 
	 */
	private Timestamp operatedAt;

	/**
	 * 文档来源,1:用户上传 2:接收的文档 3:接收的目录 4:用户创建的目录 9:公司上传文档;11:拍照转pdf的文档
	 */
	private Byte source;
	
	

	/**
	 * constructor
	 */
	public StudentDocumentDto() {
	}

	/**
	 * constructor
	 *
	 * @param studentDocument
	 */
	public StudentDocumentDto(StudentDocument studentDocument) {
		if (studentDocument != null) {
			this.setActived(studentDocument.getActived());
			this.setCommentCount(studentDocument.getCommentCount());
			this.setCreatedAt(studentDocument.getCreatedAt());
			this.setCategoryCode(studentDocument.getCategoryCode());
			this.setCategoryId(studentDocument.getCategoryId());
			this.setCommentCount(studentDocument.getCommentCount());
			this.setDocumentId(studentDocument.getDocumentId());
			this.setFileId(studentDocument.getFileId());
			this.setFileName(studentDocument.getFileName());
			this.setId(studentDocument.getId());
			this.setIntro(studentDocument.getIntro());
			this.setMajorGroupId(studentDocument.getMajorGroupId());
			this.setMarkCount(studentDocument.getMarkCount());
			this.setName(studentDocument.getName());
			this.setPageCount(studentDocument.getPageCount());
			this.setPermission(studentDocument.getPermission());
			this.setPreview(studentDocument.getPreview());
			this.setPrice(studentDocument.getPrice());
			this.setPrintCount(studentDocument.getPrintCount());
			this.setRatingCount(studentDocument.getRatingCount());
			this.setRatings(studentDocument.getRatings());
			this.setReadCount(studentDocument.getReadCount());
			this.setReviewState(studentDocument.getReviewState());
			this.setSchoolId(studentDocument.getSchoolId());
			this.setStudentId(studentDocument.getStudentId());
			this.setStudentNickName(studentDocument.getStudentNickName());
			this.setTags(studentDocument.getTags());
			this.setUpdatedAt(studentDocument.getUpdatedAt());
			this.setFileKey(studentDocument.getFileKey());
		}
	}
	
	

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getStudentIntro() {
		return studentIntro;
	}

	public void setStudentIntro(String studentIntro) {
		this.studentIntro = studentIntro;
	}

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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 未审核通过的文档不需要返回评论数
	 * @return 处理后的评论数
	 */
	public Integer getCommentCount() {
		if (reviewState != null && reviewState.intValue() == 1) {
			return null;
		}
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	/**
	 * 未审核通过的文档不需要返回收藏数
	 * @return 处理后的收藏数
	 */
	public Integer getMarkCount() {
		if (reviewState != null && reviewState.intValue() == 1) {
			return null;
		}
		return markCount;
	}

	public void setMarkCount(Integer markCount) {
		this.markCount = markCount;
	}

	/**
	 * 未审核通过的文档不需要返回打印数
	 * @return 处理后的打印数
	 */
	public Integer getPrintCount() {
		if (this.reviewState != null && this.reviewState.intValue() == 1) {
			return null;
		} else {
			Timestamp tempCreatedAt = this.documentCreatedAt;
			if (this.documentCreatedAt == null) {
				tempCreatedAt = createdAt;
			}
			return StdocDtoHelper.processPrintCount(id, printCount, pageCount, tempCreatedAt);
		}
	}

	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}

	/**
	 * 未审核通过的文档不需要返回阅读数
	 * @return 处理后的阅读数
	 */
	public Integer getReadCount() {
		if (reviewState != null && reviewState.intValue() == 1) {
			return null;
		} else {
			Timestamp tempCreatedAt = this.documentCreatedAt;
			if (this.documentCreatedAt == null) {
				tempCreatedAt = createdAt;
			}
			return StdocDtoHelper.processReadCount(id, readCount, pageCount, tempCreatedAt);
		}
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	@JsonIgnore
	public Integer getRatings() {
		return ratings;
	}

	public void setRatings(Integer ratings) {
		this.ratings = ratings;
	}

	@JsonIgnore
	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
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

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public Long getMarkRecordId() {
		return markRecordId;
	}

	public void setMarkRecordId(Long markRecordId) {
		this.markRecordId = markRecordId;
	}

	public Long getRatingRecordId() {
		return ratingRecordId;
	}

	public void setRatingRecordId(Long ratingRecordId) {
		this.ratingRecordId = ratingRecordId;
	}

	public void setThumbnail(Integer thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * 分客户端返回缩略图
	 * @return 处理后的缩略图url
	 */
	public String getThumbnailUrl() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		thumbnailUrl = null;
		if ((this.thumbnail != null && this.thumbnail.intValue() == 1) && !request.getRequestURL().toString().contains("/rest/")) {
			String tempThumbnail = this.getId() + "_thumbnail_GB";
			thumbnailUrl = ThumbnailAliyunOSSHelper.getInstance().getVisitUrl(tempThumbnail);
		} else if (StringUtils.isNotBlank(this.fileKey)) {
			String tempThumbnail = this.fileKey + "-thumbnail";
			thumbnailUrl = ThumbnailAliyunOSSHelper.getInstance().getVisitUrl(tempThumbnail);
		}
		return thumbnailUrl;
	}

	public Integer getPersonalScore() {
		return personalScore;
	}

	public void setPersonalScore(Integer personalScore) {
		this.personalScore = personalScore;
	}

	/**
	 * 处理平均分，根据评分总和 和 评分人数
	 * @return 平均评分
	 */
	public Double getAverageScore() {
		Double averageScore = 0d;
		if (ratings != null && ratingCount != null && (ratings.intValue() > 0) && (ratingCount.intValue() > 0)) {
			DecimalFormat df = new DecimalFormat("0.00");// 格式化小数，不足的补0
			averageScore = Double.valueOf(df.format(ratings.doubleValue() / ratingCount));
		}
		return averageScore;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * 根据文件名 返回 extension
	 * @return 处理后的extension
	 */
	public String getExtension() {
		if (StringUtils.isNotBlank(fileName)) {
			return FileTypeUtil.getFileExtension(fileName);
		}
		return "";
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getSenderNickName() {
		return senderNickName;
	}

	public void setSenderNickName(String senderNickName) {
		this.senderNickName = senderNickName;
	}

	public void setDocumentCreatedAt(Timestamp documentCreatedAt) {
		this.documentCreatedAt = documentCreatedAt;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getPointMemo() {
		return pointMemo;
	}

	public void setPointMemo(String pointMemo) {
		this.pointMemo = pointMemo;
	}

	public Timestamp getPastReadedAt() {
		return pastReadedAt;
	}

	public void setPastReadedAt(Timestamp pastReadedAt) {
		this.pastReadedAt = pastReadedAt;
	}

	public Timestamp getOperatedAt() {
		return operatedAt;
	}

	public void setOperatedAt(Timestamp operatedAt) {
		this.operatedAt = operatedAt;
	}

	public Byte getSource() {
		return source;
	}

	public void setSource(Byte source) {
		this.source = source;
	}

}

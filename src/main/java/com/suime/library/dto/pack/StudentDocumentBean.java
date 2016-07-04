package com.suime.library.dto.pack;

import java.sql.Timestamp;

import com.confucian.framework.support.Constants;
import com.suime.context.model.StudentDocument;
import org.apache.commons.lang3.StringUtils;

/**
 * studentDocumentBean
 *
 * @author ice
 */
public class StudentDocumentBean {

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
	 * 文档标签id
	 */
	private Long tagsId;

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
	 * 文档目录
	 */
	private Long directoryId;

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

	public Long getTagsId() {
		return tagsId;
	}

	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
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

	public Long getDirectoryId() {
		return directoryId;
	}

	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	/**
	 * transToStudentDocument
	 *
	 * @return studentDocument
	 */
	public StudentDocument transToStudentDocument() {
		StudentDocument studentDocument = new StudentDocument();
		studentDocument.setActived(this.getActived());
		studentDocument.setPermission(this.getPermission());
		studentDocument.setPrice(this.getPrice());
		studentDocument.setStudentId(this.getStudentId());
		studentDocument.setIntro(this.getIntro());
		String tempTags = this.getTags();
		if (StringUtils.isNotBlank(tempTags)) {
			tempTags = tempTags.replaceAll("，", Constants.VALUE_SIMPLE_SPLIT_CHAR);
			if (!tempTags.trim().startsWith(Constants.VALUE_SIMPLE_SPLIT_CHAR)) {
				tempTags = Constants.VALUE_SIMPLE_SPLIT_CHAR + tempTags;
			}
			if (!tempTags.endsWith(Constants.VALUE_SIMPLE_SPLIT_CHAR)) {
				tempTags += Constants.VALUE_SIMPLE_SPLIT_CHAR;
			}
			studentDocument.setTags(tempTags);
		}
		studentDocument.setName(this.getName());
		studentDocument.setFileName(this.getFileName());
		studentDocument.setSource(this.getSource());
		studentDocument.setRecommended(this.getRecommended());

		int zero = 0;
		studentDocument.setCommentCount(zero);
		studentDocument.setMarkCount(zero);
		studentDocument.setPageCount(zero);
		studentDocument.setPrintCount(zero);
		studentDocument.setRatingCount(zero);
		studentDocument.setRatings(zero);
		studentDocument.setReadCount(zero);
		studentDocument.setThumbnail(zero);
		return studentDocument;
	}
}

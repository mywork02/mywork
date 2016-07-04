package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_document 实体类
 * Created by ice 15/02/2016.
 */
public class Document implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -2961944241010590799L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	/**
	 * actived
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
	 * fileName
	 */
	private String fileName;

	/**
	 * intro
	 */
	private String intro;

	/**
	 * name
	 */
	private String name;

	/**
	 * tags
	 */
	private String tags;

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
	 * fileId
	 */
	private Long fileId;

	/**
	 * keyWords
	 */
	private String keyWords;

	/**
	 * content
	 */
	private String content;

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

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
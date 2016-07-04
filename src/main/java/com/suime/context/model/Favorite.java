package com.suime.context.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * wenku_doc_favorite 实体类
 * Created by chenqy 20/04/2016.
 */
public class Favorite implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 5858642286291233162L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * name
	 */
	private String name;

	/**
	 * 简介
	 */
	private String intro;

	/**
	 * studentId
	 */
	private Long studentId;

	/**
	 * 创建者名称
	 */
	private String studentName;

	/**
	 * 文档数量
	 */
	private Integer documentCount;

	/**
	 * 关注人数(2016-05-25需求整理时废弃,先做保留字段暂不使用)
	 */
	@Deprecated
	private Integer concernCount;

	/**
	 * actived
	 */
	private Byte actived;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIntro() {
		return intro;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setDocumentCount(Integer documentCount) {
		this.documentCount = documentCount;
	}

	public Integer getDocumentCount() {
		return documentCount;
	}

	@Deprecated
	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}

	@Deprecated
	public Integer getConcernCount() {
		return concernCount;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	public Byte getActived() {
		return actived;
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
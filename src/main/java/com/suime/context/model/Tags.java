package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_doc_tags 实体类
 * Created by ice 15/02/2016.
 */
public class Tags implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 6331626437081847500L;

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
	 * * 来源,1:管理员添加,2:用户添加
	 */
	private Byte source;

	/**
	 * 标签文本
	 */
	private String text;

	/**
	 * 标签类型,1:为普通类型,此时 category 为标签所属分类;9:为搜索类型,此时的标签是用来搜索的,category 为搜索分类 ;3:课程专题类型
	 */
	private Byte type;

	/**
	 * 使用数量
	 */
	private Integer useCount;

	/**
	 * 添加人id,
	 */
	private Long userId;

	/**
	 * categoryId
	 */
	private Long categoryId;

	/**
	 * 推荐标签对应的photo,for移动端使用
	 */
	private String photoUrl;

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

	public void setSource(Byte source) {
		this.source = source;
	}

	public Byte getSource() {
		return source;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getType() {
		return type;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

}
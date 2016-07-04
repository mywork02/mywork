package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenku_activity 实体类
 * Created by ice 15/02/2016.
 */
public class Activity implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 3631410547813982085L;

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
	 * 背景
	 */
	private String background;

	/**
	 * 活动code
	 */
	private String code;

	/**
	 * 关键词
	 */
	private String keyWords;

	/**
	 * 移动端banner
	 */
	private String mobileBanner;

	/**
	 * pc端banner
	 */
	private String pcBanner;

	/**
	 * 活动子标题
	 */
	private String subTitles;

	/**
	 * 活动标题
	 */
	private String title;

	/**
	 * 顺序,值越大越靠前
	 */
	private Integer orderNum;

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

	public void setBackground(String background) {
		this.background = background;
	}

	public String getBackground() {
		return background;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setMobileBanner(String mobileBanner) {
		this.mobileBanner = mobileBanner;
	}

	public String getMobileBanner() {
		return mobileBanner;
	}

	public void setPcBanner(String pcBanner) {
		this.pcBanner = pcBanner;
	}

	public String getPcBanner() {
		return pcBanner;
	}

	public void setSubTitles(String subTitles) {
		this.subTitles = subTitles;
	}

	public String getSubTitles() {
		return subTitles;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

}
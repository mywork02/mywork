package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_app_hit_log 实体类
 * Created by ice 12/03/2016.
 */
public class AppHitLog implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -2863290046323017736L;

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
	 * mobile 类型(android or ios),当type为mobile时,有意义
	 */
	private String mobileType;

	/**
	 * 设备类型(pc or mobile)
	 */
	private String type;

	/**
	 * 浏览器userAgent
	 */
	private String userAgent;

	/**
	 * 点击来源,1:短信;2:网站;3:传单。默认网站
	 */
	private Byte source;

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

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getMobileType() {
		return mobileType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setSource(Byte source) {
		this.source = source;
	}

	public Byte getSource() {
		return source;
	}

}
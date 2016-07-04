package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_device_token 实体类
 * Created by ice 22/03/2016.
 */
public class DeviceToken implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = -5462374177526423892L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 设备类型,1:ios,2:android
	 */
	private Byte deviceType;

	/**
	 * token
	 */
	private String token;

	/**
	 * 用户id，和user_type结合使用,当user_type为1时,表示为学生id,当user_type为2时,表示为printshopId
	 */
	private Long userId;

	/**
	 * 用户类型,1:students,2:printshop
	 */
	private Byte userType;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setDeviceType(Byte deviceType) {
		this.deviceType = deviceType;
	}

	public Byte getDeviceType() {
		return deviceType;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public Byte getUserType() {
		return userType;
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
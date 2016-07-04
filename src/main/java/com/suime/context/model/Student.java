package com.suime.context.model;

import java.sql.Timestamp;
import java.io.Serializable;

/**
 * wenji_student 实体类
 * Created by ice 15/02/2016.
 */
public class Student implements Serializable {
	/**
	 * sid
	 */
	private static final long serialVersionUID = 4021294017226061036L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * cellphone
	 */
	private String cellphone;

	/**
	 * password
	 */
	private String password;

	/**
	 * majorGroupId
	 */
	private String majorGroupId;

	/**
	 * startYear
	 */
	private Integer startYear;

	/**
	 * schoolId
	 */
	private Long schoolId;

	/**
	 * buildingId
	 */
	private Long buildingId;

	/**
	 * room
	 */
	private String room;

	/**
	 * cash
	 */
	private Integer cash;

	/**
	 * allowAds
	 */
	private Byte allowAds;

	/**
	 * firstPrintedAt
	 */
	private Timestamp firstPrintedAt;

	/**
	 * defaultPrintshopId
	 */
	private Long defaultPrintshopId;

	/**
	 * createdAt
	 */
	private Timestamp createdAt;

	/**
	 * updatedAt
	 */
	private Timestamp updatedAt;

	/**
	 * printshop头像 存放url
	 */
	private String avatar;

	/**
	 * nickName
	 */
	private String nickName;

	/**
	 * 用户来源,(第三方客户来源值为:partner-customer-{code})
	 */
	private String source;

	/**
	 * status
	 */
	private Integer status;

	/**
	 * invitecode
	 */
	private String inviteCode;

	/**
	 * 关联的printshop_id
	 */
	private Long linkedPrintshopId;
	/**
	 * 积分，累计总积分，不会减少
	 */
	private Long point;

	/**
	 * 可用积分，消费后会减少
	 */
	private Integer avliablePoint;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setMajorGroupId(String majorGroupId) {
		this.majorGroupId = majorGroupId;
	}

	public String getMajorGroupId() {
		return majorGroupId;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoom() {
		return room;
	}

	public void setCash(Integer cash) {
		this.cash = cash;
	}

	public Integer getCash() {
		return cash;
	}

	public void setAllowAds(Byte allowAds) {
		this.allowAds = allowAds;
	}

	public Byte getAllowAds() {
		return allowAds;
	}

	public void setFirstPrintedAt(Timestamp firstPrintedAt) {
		this.firstPrintedAt = firstPrintedAt;
	}

	public Timestamp getFirstPrintedAt() {
		return firstPrintedAt;
	}

	public void setDefaultPrintshopId(Long defaultPrintshopId) {
		this.defaultPrintshopId = defaultPrintshopId;
	}

	public Long getDefaultPrintshopId() {
		return defaultPrintshopId;
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

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public void setLinkedPrintshopId(Long linkedPrintshopId) {
		this.linkedPrintshopId = linkedPrintshopId;
	}

	public Long getLinkedPrintshopId() {
		return linkedPrintshopId;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public Integer getAvliablePoint() {
		return avliablePoint;
	}

	public void setAvliablePoint(Integer avliablePoint) {
		this.avliablePoint = avliablePoint;
	}
}
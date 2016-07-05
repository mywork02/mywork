package me.sui.api.dto;

import java.io.Serializable;
import java.util.Date;

public class GetSendDocMsgDataDto implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;

    /**
     * 是否有效 1：有效，0：无效
     */
    private Integer actived;

    /**
     * 发送事件名称
     */
    private String eventName;

    /**
     * 请求api的id,  wenji_api_request.WENJI_API_REQUEST_ID
     */
    private Long associatedId;

    /**
     * 关联的发送中间表id
     */
    private Long sendCacheId;

    /**
     * 接收者id
     */
    private Long receiverId;

    /**
     * 接受目录的ID
     */
    private Long receiverDirId;

    /**
     * 发送者id
     */
    private Long senderId;

    /**
     * 是否接收，0:未处理,1：接收，2：拒收
     */
    private Integer isAccepted;

    /**
     * 数据处理状态 0:初始值 1:处理中 2:处理成功 9:处理失败
     */
    private Integer dealStatus;

    /**
     * 版本号
     */
    private Integer recordVersion;

    /**
     * 处理结果信息
     */
    private String dealResult;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getActived() {
		return actived;
	}

	public void setActived(Integer actived) {
		this.actived = actived;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Long getAssociatedId() {
		return associatedId;
	}

	public void setAssociatedId(Long associatedId) {
		this.associatedId = associatedId;
	}

	public Long getSendCacheId() {
		return sendCacheId;
	}

	public void setSendCacheId(Long sendCacheId) {
		this.sendCacheId = sendCacheId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public Long getReceiverDirId() {
		return receiverDirId;
	}

	public void setReceiverDirId(Long receiverDirId) {
		this.receiverDirId = receiverDirId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Integer getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Integer isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public Integer getRecordVersion() {
		return recordVersion;
	}

	public void setRecordVersion(Integer recordVersion) {
		this.recordVersion = recordVersion;
	}

	public String getDealResult() {
		return dealResult;
	}

	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
}
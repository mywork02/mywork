package com.suime.library.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.ioc.SpringContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suime.common.support.Configure;

/**
 * 文库消息
 *
 * @author ice
 */
public class MessageDto implements Serializable {

	private static final long serialVersionUID = -730468499648313377L;
	/**
	 * id
	 */
	private Long id;

	/**
	 * 消息相关的文档
	 */
	private Long studentDocumentId;

	/**
	 * 文档名称
	 */
	private String studentDocumentName;

	/**
	 * 事件发起人
	 */
	private Long senderId;

	/**
	 * 事件发起人
	 */
	private StudentDto sender;

	/**
	 * 接收人
	 */
	private Long receiverId;

	/**
	 * 消息内容
	 */
	private String content;

	/**
	 * 关联的id,和 messageType结合使用,如messageType为1,则该id表示文档id;如果messageType为6或7,则该id为评论id
	 */
	private Long relatedId;

	// /**
	// * 评论id
	// */
	// private Long commentId;

	/**
	 * 消息种类,
	 * 1:文档收藏,2:文档分享,3:文档打印成功,4:文档通过审核,5:文档下架,6:文档评论,7:回复评论
	 */
	private Byte messageType;

	/**
	 * 消息类型,web;searchWenku 等
	 */
	private String type;

	/**
	 * extraKey 扩展对象key
	 */
	private String extraKey;

	/**
	 * extra扩展类型
	 */
	@SuppressWarnings("unused")
	private String extra;

	/**
	 * 是否有效(用于软删除),1:有效 0:无效.默认为0
	 */
	private Byte actived;

	/**
	 * 是否已阅读,0:未阅读,1:已阅读,默认为:0
	 */
	private Byte isReaded;

	/**
	 * 创建时间
	 */
	private Timestamp createdAt;

	/**
	 * 更新时间
	 */
	private Timestamp updatedAt;

	/**
	 * 评论内容
	 */
	private String commentContent;

	/**
	 * 评论状态,是否已删除
	 */
	private Byte commentActived;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentDocumentId() {
		return studentDocumentId;
	}

	public void setStudentDocumentId(Long studentDocumentId) {
		this.studentDocumentId = studentDocumentId;
	}

	public String getStudentDocumentName() {
		return studentDocumentName;
	}

	public void setStudentDocumentName(String studentDocumentName) {
		this.studentDocumentName = studentDocumentName;
	}

	@JsonIgnore
	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	/**
	 * 如果消息没有发送人，则默认发送人为21用户
	 *
	 * @return student dto
	 */
	public StudentDto getSender() {
		if (sender == null) {
			sender = new StudentDto();
			sender.setId(21L);
			sender.setNickName("系统通知");
			sender.setAvatar(Configure.getPropertyValue("student.avatar.sys_notice"));
		}
		return sender;
	}

	public void setSender(StudentDto sender) {
		this.sender = sender;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * 简单处理消息的内容显示
	 *
	 * @return 处理后的消息内容
	 */
	public String getContent() {
		if (StringUtils.equals("comment", type)) {
			String tempContent;
			if (messageType.intValue() == 6) {
				tempContent = "评论了:";
			} else {
				tempContent = "回复了:";
			}
			tempContent += commentContent;
			if (this.commentActived != null && this.commentActived.intValue() == 0) {
				tempContent = SpringContext.getText("comment.already.removed");
			}
			return tempContent;
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public Byte getMessageType() {
		return messageType;
	}

	public void setMessageType(Byte messageType) {
		this.messageType = messageType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setExtraKey(String extraKey) {
		this.extraKey = extraKey;
	}

	/**
	 * 处理扩展内容，方便移动端处理
	 *
	 * @return 处理后的扩展内容
	 */
	public Object getExtra() {
		JSONObject jsonObject = new JSONObject();
		if (StringUtils.equals("preview", type) || StringUtils.equals("comment", type)) {
			JSONObject stdocJson = new JSONObject();
			stdocJson.put("id", this.studentDocumentId);
			jsonObject.put(this.extraKey, stdocJson);
			if (StringUtils.equals("comment", type)) {
				JSONObject commentJson = new JSONObject();
				commentJson.put("id", this.relatedId);
				jsonObject.put(this.type, commentJson);
			}
			return jsonObject;
		}
		return null;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@JsonIgnore
	public Byte getActived() {
		return actived;
	}

	public void setActived(Byte actived) {
		this.actived = actived;
	}

	public Byte getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(Byte isReaded) {
		this.isReaded = isReaded;
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

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	@JsonIgnore
	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentActived(Byte commentActived) {
		this.commentActived = commentActived;
	}

	/**
	 * getTitle
	 * @return title
	 */
	// 1:文档收藏,2:文档分享,3:文档打印成功,4:文档通过审核,5:文档下架,6:文档评论,7:回复评论,8:目录收藏
	public String getTitle() {
		String title = null;
		if (this.messageType != null) {
			switch (this.messageType.intValue()) {
			case 1:
				title = SpringContext.getText("message.mark.title", this.getStudentDocumentName());
				break;
			case 2:
				break;
			case 3:
				title = SpringContext.getText("message.print.title", this.getStudentDocumentName());
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				title = SpringContext.getText("message.comment.title", this.getStudentDocumentName());
				break;
			case 7:
				title = SpringContext.getText("message.reply_comment.title");
				break;
			case 8:
				title = SpringContext.getText("message.mark_directory.title", this.getStudentDocumentName());
				break;
			default:
				break;
			}
		}
		return title;
	}
}

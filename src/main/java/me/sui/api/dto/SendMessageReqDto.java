package me.sui.api.dto;

import java.io.Serializable;

import me.sui.api.constants.BCodeEnum;
import me.sui.api.constants.SendTypeEnum;

public class SendMessageReqDto implements Serializable {

	private String sourceCode;// 请求源
	private String requestId;// 请求源的ID
	private String requestTime;// 请求时间YYYY-MM-DD HH:mm:ss
	private SendTypeEnum sendTypeEnum;// 发送类型
	private String sender; // 发送者
	private String receiver; // 接受者
	private String messageContent; // 发送内容
	private BCodeEnum bCodeEnum;// 业务类型

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public BCodeEnum getbCodeEnum() {
		return bCodeEnum;
	}

	public void setbCodeEnum(BCodeEnum bCodeEnum) {
		this.bCodeEnum = bCodeEnum;
	}

	public SendTypeEnum getSendTypeEnum() {
		return sendTypeEnum;
	}

	public void setSendTypeEnum(SendTypeEnum sendTypeEnum) {
		this.sendTypeEnum = sendTypeEnum;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}

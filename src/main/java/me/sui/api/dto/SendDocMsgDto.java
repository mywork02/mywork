package me.sui.api.dto;

import java.io.Serializable;

public class SendDocMsgDto implements Serializable {
	private String sendName;// 发送者 学生姓名或者 学生ID
	private String msg;// 消息内容
	private Integer stats;// 0:未接受 1:已接受
	private String callBackUrl;// 接受url
	private String receiverTime;// 接受时间

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getReceiverTime() {
		return receiverTime;
	}

	public void setReceiverTime(String receiverTime) {
		this.receiverTime = receiverTime;
	}

}

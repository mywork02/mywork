package me.sui.api.dto;

import java.io.Serializable;
import java.util.List;

public class PushSendDocMsgReqDto implements Serializable {

	private String sourceCode;// 请求源
	private String requestId;// 请求源的ID
	private String requestTime;// 请求时间YYYY-MM-DD HH:mm:ss
	private Long senderStudentId;// 发送学生ID
	private Long receiverStudentId;// 接口学生ID
	private String desc;// 文件夹或者文件名称
	private List<Long> docIds;// 对应的文件ID或者目录ID
	private List<Long> dirIds;// 对应的文件ID或者目录ID

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

	public Long getSenderStudentId() {
		return senderStudentId;
	}

	public void setSenderStudentId(Long senderStudentId) {
		this.senderStudentId = senderStudentId;
	}

	public Long getReceiverStudentId() {
		return receiverStudentId;
	}

	public void setReceiverStudentId(Long receiverStudentId) {
		this.receiverStudentId = receiverStudentId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Long> getDocIds() {
		return docIds;
	}

	public void setDocIds(List<Long> docIds) {
		this.docIds = docIds;
	}

	public List<Long> getDirIds() {
		return dirIds;
	}

	public void setDirIds(List<Long> dirIds) {
		this.dirIds = dirIds;
	}

}

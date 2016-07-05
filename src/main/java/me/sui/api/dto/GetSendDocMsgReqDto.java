package me.sui.api.dto;

import java.io.Serializable;

public class GetSendDocMsgReqDto implements Serializable {

	private String sourceCode;// 请求源
	private String requestId;// 请求源的ID
	private String requestTime;// 请求时间YYYY-MM-DD HH:mm:ss
	private Long receiverStudentId;// 接受消息的学生ID
	private Integer isAccepted;// 接受状态 为null查询所有状态
	private int pageNum;// 当前页
	private int pageSize;// 每页条数

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
	public Long getReceiverStudentId() {
		return receiverStudentId;
	}
	public void setReceiverStudentId(Long receiverStudentId) {
		this.receiverStudentId = receiverStudentId;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Integer isAccepted) {
		this.isAccepted = isAccepted;
	}

}

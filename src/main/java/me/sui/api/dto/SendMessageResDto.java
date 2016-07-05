package me.sui.api.dto;

import java.io.Serializable;

import me.sui.api.constants.ResStatusEnum;

public class SendMessageResDto implements Serializable{

	private String sourceCode;// 请求源
	private String requestId;// 请求源的ID
	private String responseTime;// 响应时间
	private ResStatusEnum resStatusEnum;// 请求结果
	private String errorInfo;// 报错信息

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

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public ResStatusEnum getResStatusEnum() {
		return resStatusEnum;
	}

	public void setResStatusEnum(ResStatusEnum resStatusEnum) {
		this.resStatusEnum = resStatusEnum;
	}

}

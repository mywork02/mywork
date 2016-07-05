package me.sui.api.dto.base;

import java.io.Serializable;

import me.sui.api.constants.ResStatusEnum;

public class BaseResDto implements Serializable {

	private String sourceCode;// 请求源
	private String requestId;// 请求源的ID
	private String requestTime;// 请求时间YYYY-MM-DD HH:mm:ss
	private String responseTime;// 响应时间
	private ResStatusEnum resStatusEnum;// 请求结果
	private String errorInfo;// 报错信息

	public BaseResDto() {
		this.resStatusEnum = ResStatusEnum.SUCCESS;
	}

	public BaseResDto(String errorInfo) {
		this.resStatusEnum = ResStatusEnum.ERROR;
		this.errorInfo = errorInfo;
	}


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

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public ResStatusEnum getResStatusEnum() {
		return resStatusEnum;
	}

	public void setResStatusEnum(ResStatusEnum resStatusEnum) {
		this.resStatusEnum = resStatusEnum;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}

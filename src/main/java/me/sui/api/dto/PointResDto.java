package me.sui.api.dto;

import java.io.Serializable;

import me.sui.api.constants.ResStatusEnum;

public class PointResDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6411859642806614410L;

	private ResStatusEnum resStatusEnum; // 响应结果
	private String errorInfo; // 错误信息

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

package me.sui.api.dto;

import java.io.Serializable;
import java.util.List;

public class ReceiveSendDocReqDto implements Serializable {

	private String sourceCode;// 请求源
	private String requestId;// 请求源的ID
	private String requestTime;// 请求时间YYYY-MM-DD HH:mm:ss
	private List<Long> recordIds;// 记录ID
	private Long studentId;// 接受receiver学生ID
	private boolean isAccepted;// 接受类型 true:接受 false:拒绝
	private Long receiveDirId;// 接受目录的ID

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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public List<Long> getRecordIds() {
		return recordIds;
	}

	public void setRecordIds(List<Long> recordIds) {
		this.recordIds = recordIds;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Long getReceiveDirId() {
		return receiveDirId;
	}

	public void setReceiveDirId(Long receiveDirId) {
		this.receiveDirId = receiveDirId;
	}

}

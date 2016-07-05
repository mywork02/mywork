package com.suime.common.dto.pack;

/**
 * 用户属性封装bean
 * @author ice
 */
public class UserBean {
	/**
	 * 学生id
	 */
	private String studentId;

	/**
	 * 访问者id
	 */
	private String visitorId;

	/**
	 * 是否学生,true:学生,false:访问者
	 */
	private Boolean studentFlag;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public Boolean isStudentFlag() {
		return studentFlag;
	}

	public void setStudentFlag(Boolean studentFlag) {
		this.studentFlag = studentFlag;
	}

	@Override
	public String toString() {
		return studentFlag ? studentId : visitorId;
	}
}

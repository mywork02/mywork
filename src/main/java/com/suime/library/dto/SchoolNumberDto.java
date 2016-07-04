package com.suime.library.dto;

import java.io.Serializable;

/**
 * SchoolNumberDto
 * @author lsw
 */
public class SchoolNumberDto implements Serializable {

	/**
	 * sid
	 */
	private static final long serialVersionUID = 1813173917246257529L;

	/**
	 * schoolId
	 */
	private Long schoolId;

	/**
	 * schoolName
	 */
	private String schoolName;

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

}

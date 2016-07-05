package me.sui.api.dto.doc;

import me.sui.api.dto.base.BaseReqDto;

public class DeleteDocSetReqDto extends BaseReqDto {

	/**
	 * 文集ID
	 */
	private Long setId;

	/**
	 * 学生ID
	 */
	private Long studentId;

	/**
	 * @see #setId
	 */
	public Long getSetId() {
		return setId;
	}

	/**
	 * @see #setId
	 */
	public void setSetId(Long setId) {
		this.setId = setId;
	}

	/**
	 * @see #studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @see #studentId
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

}
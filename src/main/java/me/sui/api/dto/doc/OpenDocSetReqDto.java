package me.sui.api.dto.doc;

import me.sui.api.dto.base.BaseReqDto;

public class OpenDocSetReqDto extends BaseReqDto {

	/**
	 * 文集ID
	 */
	private Long setId;

	/**
	 * 学生ID
	 */
	private Long studentId;
	/**
	 * 当前页码
	 */
	private int pageNum;// 当前页

	/**
	 * 每页条数
	 */
	private int pageSize;// 每页条数

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

	/**
	 * @see #pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @see #pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @see #pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @see #pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
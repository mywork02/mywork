package com.suime.library.dto.pack;

/**
 * DocSetBean
 * @author clj
 */
public class DocSetBean {

	/**
	 * 文集ID
	 */
	private Long setId;

	/**
	 * 文集名称
	 */
	private String setName;

	/**
	 * 文集说明
	 */
	private String setDesc;

	/**
	 * 文集封面
	 */
	private String setCover;

	/**
	 * 学生ID
	 */
	private Long studentId;

	/**
	 * @see #setName
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * @see #setName
	 */
	public void setSetName(String setName) {
		this.setName = setName;
	}

	/**
	 * @see #setDesc
	 */
	public String getSetDesc() {
		return setDesc;
	}

	/**
	 * @see #setDesc
	 */
	public void setSetDesc(String setDesc) {
		this.setDesc = setDesc;
	}

	/**
	 * @see #setCover
	 */
	public String getSetCover() {
		return setCover;
	}

	/**
	 * @see #setCover
	 */
	public void setSetCover(String setCover) {
		this.setCover = setCover;
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

}

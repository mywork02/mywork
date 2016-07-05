package me.sui.api.dto.doc;

import me.sui.api.dto.base.BaseReqDto;

public class RemoveDocFromSetReqDto extends BaseReqDto {

	/**
	 * 中间表ID
	 */
	private Long refId;

	/**
	 * 文集ID
	 */
	private Long setId;

	/**
	 * 文件类型 1:文档 2:文件夹
	 */
	private Integer docType;

	/**
	 * 文档/文件夹ID
	 */
	private Long docId;

	/**
	 * 学生ID
	 */
	private Long studentId;

	/**
	 * 
	 */
	private String extension;

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
	 * @see #docType
	 */
	public Integer getDocType() {
		return docType;
	}

	/**
	 * @see #docType
	 */
	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	/**
	 * @see #docId
	 */
	public Long getDocId() {
		return docId;
	}

	/**
	 * @see #docId
	 */
	public void setDocId(Long docId) {
		this.docId = docId;
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
	 * @see #extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @see #extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @see #refId
	 */
	public Long getRefId() {
		return refId;
	}

	/**
	 * @see #refId
	 */
	public void setRefId(Long refId) {
		this.refId = refId;
	}

}
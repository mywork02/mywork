package me.sui.api.dto.doc;

import me.sui.api.dto.base.BaseReqDto;

public class UpdateDocSetReqDto extends BaseReqDto {
	/**
	 * 文件ID
	 */
	private Long setId;//文件ID
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

}
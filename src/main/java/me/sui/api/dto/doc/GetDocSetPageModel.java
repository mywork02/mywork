package me.sui.api.dto.doc;

import java.io.Serializable;
import java.util.List;

import me.sui.api.dto.base.PageInfo;

public class GetDocSetPageModel implements Serializable {

	private List<GetDocSetDataDto> getDocSetDataDto;

	private PageInfo pageInfo;

	/**
	 * @see #getDocSetDataDto
	 */
	public List<GetDocSetDataDto> getGetDocSetDataDto() {
		return getDocSetDataDto;
	}

	/**
	 * @see #getDocSetDataDto
	 */
	public void setGetDocSetDataDto(List<GetDocSetDataDto> getDocSetDataDto) {
		this.getDocSetDataDto = getDocSetDataDto;
	}

	/**
	 * @see #pageInfo
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @see #pageInfo
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}

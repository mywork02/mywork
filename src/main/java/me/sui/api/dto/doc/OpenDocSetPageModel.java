package me.sui.api.dto.doc;

import java.io.Serializable;
import java.util.List;

import me.sui.api.dto.base.PageInfo;

public class OpenDocSetPageModel implements Serializable {

	private List<OpenDocSetDataDto> openDocSetDataDto;

	private PageInfo pageInfo;

	/**
	 * @see #openDocSetDataDto
	 */
	public List<OpenDocSetDataDto> getOpenDocSetDataDto() {
		return openDocSetDataDto;
	}

	/**
	 * @see #openDocSetDataDto
	 */
	public void setOpenDocSetDataDto(List<OpenDocSetDataDto> openDocSetDataDto) {
		this.openDocSetDataDto = openDocSetDataDto;
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

package me.sui.api.dto.balance;

import java.io.Serializable;
import java.util.List;

import me.sui.api.dto.base.PageInfo;

public class GetBalanceDetailPageModel implements Serializable {

	private List<GetBalanceDetailDataDto> getBalanceDetailDataDto;

	private PageInfo pageInfo;

	public List<GetBalanceDetailDataDto> getGetBalanceDetailDataDto() {
		return getBalanceDetailDataDto;
	}

	public void setGetBalanceDetailDataDto(List<GetBalanceDetailDataDto> getBalanceDetailDataDto) {
		this.getBalanceDetailDataDto = getBalanceDetailDataDto;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

}

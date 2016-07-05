package me.sui.api.dto.balance;

import java.util.List;

import me.sui.api.dto.base.BaseReqDto;

public class AddBalanceDetailReqDto extends BaseReqDto {

	private List<BalanceDetailDto> bdDtos;

	/**
	 * @see #bdDtos
	 */
	public List<BalanceDetailDto> getBdDtos() {
		return bdDtos;
	}

	/**
	 * @see #bdDtos
	 */
	public void setBdDtos(List<BalanceDetailDto> bdDtos) {
		this.bdDtos = bdDtos;
	}

}
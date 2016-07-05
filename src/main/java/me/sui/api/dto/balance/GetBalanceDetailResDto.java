package me.sui.api.dto.balance;

import me.sui.api.dto.base.BaseResDto;

public class GetBalanceDetailResDto extends BaseResDto {

	private GetBalanceDetailPageModel result;

	public GetBalanceDetailPageModel getResult() {
		return result;
	}

	public void setResult(GetBalanceDetailPageModel result) {
		this.result = result;
	}
}

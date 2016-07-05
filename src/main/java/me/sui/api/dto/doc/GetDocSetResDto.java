package me.sui.api.dto.doc;

import me.sui.api.dto.base.BaseResDto;

public class GetDocSetResDto extends BaseResDto {
	private GetDocSetPageModel result;

	/**
	 * @see #result
	 */
	public GetDocSetPageModel getResult() {
		return result;
	}

	/**
	 * @see #result
	 */
	public void setResult(GetDocSetPageModel result) {
		this.result = result;
	}

}
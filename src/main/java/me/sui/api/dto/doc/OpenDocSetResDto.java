package me.sui.api.dto.doc;

import me.sui.api.dto.base.BaseResDto;

public class OpenDocSetResDto extends BaseResDto {
	private OpenDocSetPageModel result;

	/**
	 * @see #result
	 */
	public OpenDocSetPageModel getResult() {
		return result;
	}

	/**
	 * @see #result
	 */
	public void setResult(OpenDocSetPageModel result) {
		this.result = result;
	}

}
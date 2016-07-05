package me.sui.api.constants;

/**
 * Created by ice on 24/3/2016.
 */
public enum ResStatusEnum {
	/**
	 * 成功
	 */
	SUCCESS("SUCCESS"),
	/**
	 * 失败
	 */
	ERROR("ERROR"),

	//
	;
	//

	private String code;

	ResStatusEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

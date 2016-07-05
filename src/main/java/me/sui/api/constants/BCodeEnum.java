package me.sui.api.constants;

public enum BCodeEnum {
	/**
	 * PC端注册
	 */
	REGISTER_PC("REGISTER_PC"),

	/**
	 * IOS端注册
	 */
	REGISTER_IOS("REGISTER_IOS"),

	/**
	 * Andriod端注册
	 */
	REGISTER_AND("REGISTER_AND"),
	/**
	 * 营销
	 */
	REGISTER_MARK("REGISTER_MARK"),

	/**
	 * 自动歇业通知
	 */
	AUTO_SUSPEND_PRINTSHOP("AUTO_SUSPEND_PRINTSHOP"),

	;

	private String code;

	public String getCode() {
		return code;
	}

	BCodeEnum(String code) {
		this.code = code;
	}

}

package me.sui.api.constants;

/**
 * 文档类型
 * 1：文件夹，0：文档
 * @author davidclj
 *
 */
public enum DocTypeEnum {
	/**
	 * 0：文档
	 */
	DOC(Integer.valueOf("0")),
	/**
	 * 1：目录
	 */
	DIR(Integer.valueOf("1")),

	;

	private Integer code;

	public Integer getCode() {
		return code;
	}

	DocTypeEnum(Integer code) {
		this.code = code;
	}

}

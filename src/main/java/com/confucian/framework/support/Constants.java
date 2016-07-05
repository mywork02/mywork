package com.confucian.framework.support;

/**
 * 常用常量
 * @author ice
 */
public final class Constants extends ConfigurableConstants {
	static {
		init("confician.properties");
	}

	/**
	 * 正确结果标识
	 */
	public static final int NORMAL_RESULT_RIGHT = 1;

	/**
	 * 错误结果标识
	 */
	public static final int NORMAL_RESULT_ERROR = 0;

	/**
	 * request id key(rqid)
	 */
	public static final String KEY_RQID = getProperty("key.rqid", "rqid");

	/**
	 * 百分号用于模糊查询(%)
	 */
	public static final String KEY_PERCENT = getProperty("key.percent", "%");

	/**
	 * 默认每页显示数量(20)
	 */
	public static final int VALUE_PAGE_SIZE = Integer.parseInt(getProperty("value.page_size", String.valueOf(20)));

	/**
	 * 默认精度
	 */
	public static final int VALUE_DIV_SCALE = Integer.parseInt(getProperty("value.div_scale", String.valueOf(2)));

	/**
	 * 金额格式(0.00)
	 */
	public static final String VALUE_DECIMAL_PATTERN = getProperty("value.decimal_pattern", "0.00");

	/**
	 * 日期格式
	 */
	public static final String VALUE_DATE_PATTERN = getProperty("value.date_pattern", "yyyy-MM-dd");

	/**
	 * 时间格式
	 */
	public static final String VALUE_DATE_TIME_PATTERN = getProperty("value.date_time_pattern", "yyyy-MM-dd HH:mm:ss");

	/**
	 * 简单分隔符
	 */
	public static final String VALUE_SIMPLE_SPLIT_CHAR = getProperty("value.simple_split_char", ",");

	/**
	 * 点号分隔符
	 */
	public static final String VALUE_POINT_CHAR = getProperty("value.point_char", ".");

	/**
	 * 文件上传根路径
	 */
	public static final String FILE_ROOT_DIR = getProperty("value.file_root_dir", ".");
}

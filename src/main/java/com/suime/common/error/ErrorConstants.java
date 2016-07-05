package com.suime.common.error;

/**
 * 错误信息
 * @author ice
 */
public final class ErrorConstants {

	/**
	 * 未知错误
	 */
	public static final String UNKNOWN_ERROR = "99999";

	/**
	 * 参数错误
	 */
	public static final String PARAMS_ERROR = "00001";

	/**
	 * 无效账号
	 */
	public static final String ACCOUNT_INVALID = "00005";

	/**
	 * 密码格式不正确
	 */
	public static final String PASSWORD_ERROR = "00011";

	/**
	 * 验证码不匹配
	 */
	public static final String VCODE_NOTMATCH_ERROR = "00021";

	/**
	 * token 无效
	 */
	public static final String TOKEN_INVALID = "00101";

	/**
	 * 无效手机号,格式不正确
	 */
	public static final String MOBILE_ERROR = "01001";

	/**
	 * 手机号已经注册过
	 */
	public static final String MOBILE_ALREADY_REGISTERED = "01002";

	/**
	 * 不能发送短信息
	 */
	public static final String SMS_CANNOT_SEND = "01021";

	/**
	 * 等待片刻才能发送短消息
	 */
	public static final String SMS_WAIT_MINUTE = "01022";

	/**
	 * 用户不存在
	 */
	public static final String USER_NOT_EXISTS = "02001";

	/**
	 * 无效用户
	 */
	public static final String USER_INVALID = "02002";

	/**
	 * 用户已下线
	 */
	public static final String USER_OFFLINE = "02003";

	/**
	 * 用户无权限操作
	 */
	public static final String USER_NO_AUTH = "02009";

	/**
	 * 文件未找到
	 */
	public static final String FILE_NOT_FOUND = "05001";

	/**
	 * 文件无权限访问
	 */
	public static final String FILE_NO_AUTH = "05002";

	/**
	 * 文件名获取失败
	 */
	public static final String FILE_FETCH_FAILURE = "05003";

	/**
	 * 文件扩展名不能识别
	 */
	public static final String FILE_EXTENSION_ERROR = "05004";

	/**
	 * 文件上传key为空
	 */
	public static final String FILE_KEY_BLANK = "05005";

	/**
	 * 文件名为空
	 */
	public static final String FILE_NAME_BLANK = "05006";

	/**
	 * 余额不足
	 */
	public static final String BALANCE_LACK = "03001";

	/**
	 * 订单(printOrder)不存在
	 */
	public static final String PRINT_ORDER_NOT_FOUND = "04001";

	/**
	 * 订单(printOrder)不能取消
	 */
	public static final String PRINT_ORDER_CAN_NOT_CANCEL = "04002";

}

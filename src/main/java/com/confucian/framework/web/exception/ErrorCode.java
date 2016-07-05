package com.confucian.framework.web.exception;

/**
 * errorCode
 *
 * @author ice
 */
public class ErrorCode {

    /**
     * 参数错误
     */
    public static final String PARAMS_ERROR = "00001";

    /**
     * 系统错误
     */
    public static final String SYSTEM_ERROR = "00002";

    /**
     * 缺少Token
     */
    public static final String NEED_TOKEN = "00102";

    /**
     * Token过期
     */
    public static final String TOKEN_EXPIRED = "00103";

    /**
     * 用户无效。
     */
    public static final String USER_ERROR = "02002";

    /**
     * 用户未登录
     */
    public static final String USER_OFFLINE_ERROR = "02003";

    /**
     * 用户无权限操作
     */
    public static final String USER_NO_AUTH = "02009";

    /**
     * 用户名或者密码错误
     */
    public static final String USER_OR_PASSWORD_ERROR = "02007";

    /**
     * 未知错误
     */
    public static final String UNKNOWN_ERROR = "99999";
}

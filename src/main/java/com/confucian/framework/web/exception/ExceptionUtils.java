package com.confucian.framework.web.exception;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.dto.ErrorResultBean;
import com.confucian.framework.support.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CommonUtils
 *
 * @author ice
 */
public class ExceptionUtils {

    /**
     * errorCodeMap
     */
    private static Map<String, ErrorResultBean> errorCodeMap = new ConcurrentHashMap<String, ErrorResultBean>();

    /**
     * generate_result_bean
     *
     * @param result    状态
     * @param errorCode errorCode
     * @return commonResultBean
     */
    public static CommonResultBean generateResultBean(int result, String errorCode) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(result);
        if (Constants.NORMAL_RESULT_ERROR == result) {
            if (StringUtils.isBlank(errorCode)) {
                resultBean.setBody(new ErrorResultBean(ErrorCode.UNKNOWN_ERROR, "Unknown error", "未知错误"));
            } else {
                resultBean.setBody(generateErrorInfo(errorCode));
            }
        }
        return resultBean;
    }

    /**
     * generateErrorInfo
     *
     * @param errorCode errorCode
     * @return ErrorResultBean
     */
    public static ErrorResultBean generateErrorInfo(String errorCode) {
        if (errorCodeMap.get(errorCode) != null) {
            return errorCodeMap.get(errorCode);
        } else {
            ErrorResultBean result = null;
            if (ErrorCode.SYSTEM_ERROR.equals(errorCode)) {
                result = new ErrorResultBean("System error", errorCode, "系统错误");
            } else if (ErrorCode.NEED_TOKEN.equals(errorCode)) {
                result = new ErrorResultBean("Need token", errorCode, "缺少Token");
            } else if (ErrorCode.TOKEN_EXPIRED.equals(errorCode)) {
                result = new ErrorResultBean("Token expired", errorCode, "Token过期");
            } else if (ErrorCode.USER_ERROR.equals(errorCode)) {
                result = new ErrorResultBean("User invalid", errorCode, "用户无效。");
            } else if (ErrorCode.USER_OFFLINE_ERROR.equals(errorCode)) {
                result = new ErrorResultBean("User offline", errorCode, "用户未登录");
            } else if (ErrorCode.USER_NO_AUTH.equals(errorCode)) {
                result = new ErrorResultBean("User no auth", errorCode, "用户无权限进行该操作");
            } else if (ErrorCode.USER_OR_PASSWORD_ERROR.equals(errorCode)) {
                result = new ErrorResultBean("User or Password error", errorCode, "用户名或者密码错误。");
            } else {
                result = new ErrorResultBean("Unknown error", ErrorCode.UNKNOWN_ERROR, "未知错误");
            }
            errorCodeMap.put(errorCode, result);
            return result;
        }
    }

    /**
     * generateErrorInfo
     *
     * @param errorCode        错误编码
     * @param error            错误 英文描述
     * @param errorDescription 错误描述 中文描述
     * @return errorResultBean
     */
    public static ErrorResultBean generateErrorInfo(String errorCode, String error, String errorDescription) {
        ErrorResultBean result;
        if (errorCodeMap.get(errorCode) != null) {
            result = errorCodeMap.get(errorCode);
            result.setError(error);
            result.setErrorDescription(errorDescription);
        } else {
            result = new ErrorResultBean(error, errorCode, errorDescription);
            errorCodeMap.put(errorCode, result);
        }
        return result;
    }
}

package com.confucian.framework.web.exception;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.dto.ErrorResultBean;
import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 统一exception处理中心
 *
 * @author ice
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 业务exception处理
     *
     * @return error 信息
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object businessException(HttpServletRequest request, BusinessException ex) {
        this.addExceptionLogs(request, ex);
        return generateCommonResultBean(request, ex, ex.getErrorResultBean());
    }

    /**
     * 参数错误
     *
     * @param ex
     * @return error 信息
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object paramsException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        this.addExceptionLogs(request, ex);
        Object body = ExceptionUtils.generateErrorInfo(ErrorCode.PARAMS_ERROR, ex.getMessage(), SpringContext.getText("params_error"));
        return generateCommonResultBean(request, ex, body);
    }

    /**
     * 通用处理方式
     *
     * @param ex
     * @return error 信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object process(HttpServletRequest request, Exception ex) {
        this.addExceptionLogs(request, ex);
        // Object body = new ErrorResultBean(ex.getMessage(), ErrorCode.UNKNOWN_ERROR, SpringContext.getText("unkown_error"));
        Object body = new ErrorResultBean("", ErrorCode.UNKNOWN_ERROR, SpringContext.getText("unkown_error"));
        return generateCommonResultBean(request, ex, body);
    }

    /**
     * shiro 权限exception处理,未登录错误处理
     *
     * @return error 信息
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object unauthenticatedException(HttpServletRequest request, Exception ex) {
        this.addExceptionLogs(request, ex, false);
        Object body = ExceptionUtils.generateErrorInfo(ErrorCode.USER_OFFLINE_ERROR);
        return generateCommonResultBean(request, ex, body);
    }

    /**
     * shiro 权限exception处理,权限不够错误
     *
     * @return error 信息
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object unauthorizedException(HttpServletRequest request, Exception ex) {
        this.addExceptionLogs(request, ex, false);
        Object body = ExceptionUtils.generateErrorInfo(ErrorCode.USER_NO_AUTH);
        return generateCommonResultBean(request, ex, body);
    }

    /**
     * 添加exception
     *
     * @param ex
     */
    private void addExceptionLogs(HttpServletRequest request, Exception ex) {
        this.addExceptionLogs(request, ex, true);
    }

    /**
     * 添加exception
     *
     * @param ex
     */
    private void addExceptionLogs(HttpServletRequest request, Exception ex, Boolean isPrintException) {
        // LogsService logsService = (LogsService) SpringContext.getBean("logsService");
        StringBuffer sb = new StringBuffer();
        sb.append(request.getRequestURL());
        if (StringUtils.isNotBlank(request.getQueryString())) {
            sb.append("?").append(request.getQueryString());
        }
        String record = ex.getClass().getName();
        String rqid = this.extractRqid(request);
        // logsService.addExceptionLogs(sb.toString(), record, exception);
        logger.error("rqid:" + rqid + ",ip:" + WebUtils.getRemoteIp(request));
        logger.error("rqid:" + rqid + ",header token:" + request.getHeader("token") + ",param token:" + request.getParameter("token"));
        String cellphone = request.getParameter("cellphone");
        if (StringUtils.isNotBlank(cellphone)) {
            logger.error("rqid:" + rqid + ",cellphone:" + cellphone);
        }
        logger.error("rqid:" + rqid + ",request_url:" + sb.toString());
        logger.error("rqid:" + rqid + ",record:" + record);
        if (isPrintException) {
            String exception = getErrorInfoFromException(ex);
            logger.error("rqid:" + rqid + ",exception:" + exception);
        } else {
            logger.error("rqid:" + rqid + ",exception:" + ex.getMessage());
        }
    }

    /**
     * 封装生成commonResultBean
     *
     * @param request request请求
     * @param ex      exception
     * @param body    body
     * @return commonResult
     */
    private Object generateCommonResultBean(HttpServletRequest request, Exception ex, Object body) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
        resultBean.setBody(body);
        resultBean.setRqid(extractRqid(request));
        return resultBean;
    }

    /**
     * extract request请求中的rqid属性
     *
     * @param request request
     * @return rqid值
     */
    private String extractRqid(HttpServletRequest request) {
        Object rqidObj = request.getAttribute(Constants.KEY_RQID);
        String rqid = null;
        if (rqidObj != null) {
            rqid = rqidObj.toString();
        }
        return rqid;
    }

    /**
     * ex printStackTrace 转 String
     *
     * @param ex ex
     * @return string
     */
    private String getErrorInfoFromException(Exception ex) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e2) {
            return ex.getMessage();
        }
    }
}

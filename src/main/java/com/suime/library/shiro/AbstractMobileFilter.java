package com.suime.library.shiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.dto.ErrorResultBean;
import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.DigestUtil;
import com.confucian.framework.utils.JsonUtil;
import com.confucian.framework.utils.StringUtils;
import com.confucian.framework.web.exception.ExceptionUtils;
import com.suime.context.model.support.LibraryConstants;
import com.suime.library.service.DeviceTokenService;

/**
 * 移动端filter 抽象类
 * @author ice
 */
public abstract class AbstractMobileFilter implements Filter {

	/**
	 * logger
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * http uri md5 validate error
	 */
	protected final String httpUriMd5Error = "50101";
	/**
	 * 含有 MD5 参数的name
	 */
	protected String md5Signal;
	/**
	 * deviceTokenKey
	 */
	protected String deviceTokenKey;
	/**
	 * deviceType
	 */
	protected Byte deviceType = LibraryConstants.DEVICE_TYPE_ANDROID;

	/**
	 * extract 待加密校验字符串
	 * @param req
	 * @param body
	 * @param pathInfo
	 * @param rqid
	 * @return 待加密校验字符串
	 */
	protected String extractFilterStr(HttpServletRequest req, String body, String pathInfo, String rqid) {
		this.logger.info(Constants.KEY_RQID + rqid + ",req:" + req.getPathInfo());
		this.logger.info(Constants.KEY_RQID + rqid + Constants.VALUE_SIMPLE_SPLIT_CHAR + pathInfo);
		this.logger.info(Constants.KEY_RQID + rqid + Constants.VALUE_SIMPLE_SPLIT_CHAR + body);

		List<String> keylist = new ArrayList<String>();
		List<String> valuelist = new ArrayList<String>();
		Enumeration<String> mappingHeader = req.getHeaderNames();
		while (mappingHeader.hasMoreElements()) {
			String key = mappingHeader.nextElement();
			String value = StringUtils.lowerCase(req.getHeader(key));
			if (md5Signal.equalsIgnoreCase(key)) {
				continue;
			}
			String keyLower = StringUtils.lowerCase(key);
			if (keyLower.indexOf("sui_") > -1) {
				keylist.add(keyLower.replaceFirst("sui_", ""));
				valuelist.add(StringUtils.lowerCase(value));
			}
		}
		Enumeration<String> mappingParameter = req.getParameterNames();
		/**
		 * 获取inputstream
		 */
		while (mappingParameter.hasMoreElements()) {
			String key = mappingParameter.nextElement();
			String value = StringUtils.lowerCase(req.getParameter(key));
			if (md5Signal.equalsIgnoreCase(key)) {
				continue;
			}
			String keyLower = StringUtils.lowerCase(key);
			keylist.add(keyLower);
			valuelist.add(StringUtils.lowerCase(value));
		}

		extractFromRequestBody(body, keylist, valuelist);

		Collections.sort(keylist);
		Collections.sort(valuelist);
		keylist.addAll(valuelist);
		String keyValueString = StringUtils.join(keylist.toArray());
		if (StringUtils.isNotBlank(req.getHeader("token"))) {
			keyValueString += req.getHeader("token");
		}
		// keyValueString += StringUtils.lowerCase(pathInfo.substring(1));
		return keyValueString;
	}

	/**
	 * 从requestBody 中抽取数据
	 * @param body
	 * @param keylist
	 * @param valuelist
	 */
	private void extractFromRequestBody(String body, List<String> keylist, List<String> valuelist) {
		if (StringUtils.isNotBlank(body)) {
			KeyValueBean keyValueBean = IosHelper.parseKeyValue(body);
			if (keyValueBean != null) {
				List<String> keys = keyValueBean.getKeys();
				if (keys != null && !keys.isEmpty()) {
					keylist.addAll(keys);
				}
				List<String> values = keyValueBean.getValues();
				if (values != null && !values.isEmpty()) {
					valuelist.addAll(values);
				}
			}
		}
	}

	/**
	 * url 验证异常
	 * @param response
	 * @param rqid
	 * @throws IOException
	 */
	protected void throwUrlException(HttpServletResponse response, String rqid) throws IOException {
		HttpServletResponse res = response;
		res.setStatus(HttpStatus.SC_NOT_FOUND);
		res.setContentType("application/json;charset=UTF-8");
		CommonResultBean errorResultBean = new CommonResultBean();
		ErrorResultBean errorInfo = ExceptionUtils.generateErrorInfo(httpUriMd5Error, "http uri source error", SpringContext.getText("uri_source_error"));
		errorResultBean.setResult(Constants.NORMAL_RESULT_ERROR);
		errorResultBean.setBody(errorInfo);
		errorResultBean.setRqid(rqid);
		res.getOutputStream().write(JsonUtil.toJsonString(errorResultBean).getBytes("UTF-8"));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		MyRequestWrapper myRequestWrapper = generateRequestWrapper(req);

		String body = myRequestWrapper.getBody();
		String pathInfo = myRequestWrapper.getPathInfo();
		String rqid = (String) request.getAttribute(Constants.KEY_RQID);

		String keyValueString = extractFilterStr(req, body, pathInfo, rqid);
		String keyValueString2 = keyValueString;// 含有斜杠的
		keyValueString += StringUtils.lowerCase(pathInfo.substring(1));
		keyValueString2 += StringUtils.lowerCase(pathInfo);

		String md5 = DigestUtil.md5Hex(keyValueString);
		String md52 = DigestUtil.md5Hex(keyValueString2);
		String md5Parameter = req.getHeader(md5Signal);
		if (!StringUtils.equals(md5, md5Parameter) && !StringUtils.equals(md52, md5Parameter)) {
			logger.info("rqid:" + rqid + "," + "-------sb---------" + keyValueString);
			logger.info("rqid:" + rqid + "," + "-------md5---------" + md5);
			logger.info("rqid:" + rqid + "," + "-------receive md5---------" + md5Parameter);
			// logger.info("rqid:" + rqid + "," + "-------key-value---------" + StringUtils.join(keylist.toArray(), "_|_"));
			throwUrlException((HttpServletResponse) response, rqid);
			return;
		}
		try {
			String iosUdid = req.getHeader(deviceTokenKey);
			DeviceTokenService deviceTokenService = (DeviceTokenService) SpringContext.getBean("deviceTokenService");
			if (StringUtils.isNotBlank(iosUdid)) {
				Long userId = BaseUserHelper.getInstance().getUserId();
				deviceTokenService.addUserDeviceToken(userId, iosUdid, deviceType);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throwUrlException((HttpServletResponse) response, rqid);
		}
		chain.doFilter(myRequestWrapper, response);
	}

	/**
	 * request封装
	 * @param req
	 * @return requestWapper
	 * @throws IOException
	 */
	protected MyRequestWrapper generateRequestWrapper(HttpServletRequest req) throws IOException {
		MyRequestWrapper myRequestWrapper = new MyRequestWrapper(req);
		return myRequestWrapper;
	}

	@Override
	public void destroy() {

	}
}

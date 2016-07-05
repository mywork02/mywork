package com.confucian.framework.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Request相关的util类,参考自navinfo tenace
 * @author ice
 */
public final class RequestUtil {

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

	/**
	 * 禁止实例化
	 */
	private RequestUtil() {
	}

	/**
	 * 设置cookie值<br>
	 * cookie路径为"/",会话到期则cookie失效
	 * @param response response
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, "/", -1);
	}

	/**
	 * 设置cookie值 <br/>
	 * @param response response
	 * @param name cookie名称
	 * @param value cookie值
	 * @param path cookie存储路径
	 * @param age cookie生存时间，单位秒
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path, int age) {
		logger.debug("Setting cookie '" + name + "' on path '" + path + "'");
		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		if (age > 0) {
			cookie.setMaxAge(age); // 不指定过期限制，则Cookie将不会保留在客户端上，会话到期则cookie失效。
		}
		response.addCookie(cookie);
	}

	/**
	 * 根据 name得到coookie对象
	 * @param request request
	 * @param name cookie名称
	 * @return cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;
		if (cookies == null) {
			return returnCookie;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];
			if (thisCookie.getName().equals(name) && !"".equals(thisCookie.getValue())) {
				returnCookie = thisCookie;
				break;
			}
		}
		return returnCookie;
	}

	/**
	 * 删除cookie
	 * @param response response
	 * @param cookie 待删cookie
	 */
	public static void deleteCookie(HttpServletResponse response, Cookie cookie) {
		if (cookie != null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	/**
	 * 得到完整的App Url基础地址 <br>
	 * 比如https://localhost/tenace
	 * @param request request
	 * @return App Url基础地址
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if (("http".equals(scheme) && (port != 80)) || ("https".equals(scheme) && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}

	/**
	 * 通过request得到get方式的请求地址,并且过滤数据中指定的参数
	 * @param request request
	 * @param noParams 过滤参数数组,可变参数
	 * @return url结果
	 */
	public static String getRequestURL(HttpServletRequest request, String... noParams) {
		String contextUrl = request.getRequestURL().toString();

		String[] array = StringUtils.splitByWholeSeparator(request.getQueryString(), "&");
		if (array != null && array.length > 0) {
			int jtemp = 0;
			for (int i = 0; i < array.length; i++) {
				String[] item = StringUtils.splitByWholeSeparator(array[i], "=");
				String key = item[0];
				String value = item[1];

				if (ArrayUtils.contains(noParams, key)) {
					continue;
				}
				if (jtemp++ > 0) {
					contextUrl += "&" + key + "=" + value;
				} else {
					contextUrl += "?" + key + "=" + value;
				}
			}
		}
		return contextUrl;
	}

	/**
	 * 从request中获取真实的IP地址
	 * @param request http请求
	 * @return ip地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
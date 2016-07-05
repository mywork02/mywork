package com.confucian.framework.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * web 工具类
 *
 * @author ice
 */
public class WebUtils {

    /**
     * BUFFER_SIZE
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(WebUtils.class);

    /**
     * 获取请求用户的Ip
     *
     * @param request
     * @return string ip
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            for (String ip : ipList) {
                if (!"127.0.0.1".equals(StringUtils.trim(xfwd)) && !"localhost".equals(StringUtils.trim(xfwd))) {
                    return ip;
                }
            }
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取请求头的值
     *
     * @param request request
     * @return map
     */
    public static Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        Enumeration<String> it = request.getHeaderNames();
        String key = null;
        while (it.hasMoreElements()) {
            key = it.nextElement();
            result.put(key, request.getHeader(key));
        }
        return result;
    }

    /**
     * 判断两个IP是否在一个网段内
     *
     * @param dist
     * @param ip
     * @return boolean
     */
    public static boolean isIpLike(String dist, String ip) {
        if (StringUtils.isBlank(dist) || StringUtils.isBlank(ip)) {
            return false;
        }
        String std = dist.substring(0, dist.lastIndexOf("."));
        String sti = ip.substring(0, ip.lastIndexOf("."));
        if (StringUtils.equals(std, sti)) {
            return true;
        }
        return false;
    }

    /**
     * 向请求用户添加cookie值
     * 不设置cookie存在时间，则默认为session过期时间
     *
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param path
     * @param maxSecond
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, int maxSecond) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath(path);
        if (maxSecond > 0) {
            cookie.setMaxAge(maxSecond);
        }
        response.addCookie(cookie);
    }

    /**
     * addCookie 向客户端写cookie
     *
     * @param response    response
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     * @param domain      domain
     * @param path        path
     * @param maxSecond   maxSecond
     */
    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain, String path, int maxSecond) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        cookie.setPath(path);
        if (maxSecond > 0) {
            cookie.setMaxAge(maxSecond);
        }
        response.addCookie(cookie);
    }

    /**
     * 清空客户端的cookie值
     *
     * @param response
     * @param path
     * @param cookieName
     */
    public static void clearCookie(HttpServletResponse response, String path, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 清除cookie
     *
     * @param response
     * @param domain
     * @param path
     * @param cookieName
     */
    public static void clearCookie(HttpServletResponse response, String domain, String path, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setDomain(domain);
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
    }

    /**
     * 清除allCookies
     *
     * @param request
     * @param response
     * @param path
     */
    public static void clearAllCookies(HttpServletRequest request, HttpServletResponse response, String path) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1) {
            return;
        }
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }

    /**
     * 取出客户端的cookie
     *
     * @param request
     * @param cookieName
     * @return cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 取出指定的cookie值
     *
     * @param request
     * @param cookieName
     * @return string value
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 获取图片
     *
     * @param urlString
     * @return byte[]
     */
    public static byte[] getUrlPicture(String urlString) {
        InputStream input = null;
        byte[] buf = new byte[BUFFER_SIZE];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = -1;
        try {
            URL url = new URL(urlString);
            input = url.openStream();
            while ((len = input.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            return out.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * 获取tomcat中项目的context root，WEB-INF的上层目录
     *
     * @return context root
     */
    public static String getContextRoot() {
        URL url = WebUtils.class.getResource("WebUtils.class");
        String urlStr = url.toString();
        int lastIndex = urlStr.indexOf("WEB-INF");
        if (lastIndex >= 0) {
            urlStr = urlStr.substring(0, lastIndex);
        }
        if (File.separatorChar != '/') {
            urlStr = urlStr.replace("jar:", "").replace("file:/", "").replace('/', File.separatorChar);
        } else {
            urlStr = urlStr.replace("jar:", "").replace("file:", "");
        }
        return urlStr;
    }

    /**
     * inWebContainer
     *
     * @return boolean
     */
    public static boolean inWebContainer() {
        URL url = WebUtils.class.getResource("WebUtils.class");
        String urlStr = url.toString();
        int lastIndex = urlStr.indexOf("WEB-INF");
        return lastIndex >= 0;
    }

    /**
     * getHttpBody
     *
     * @param br
     * @return httpBody
     */
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        String str = "";
        try {
            while ((inputLine = br.readLine()) != null) {
                str += inputLine;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return str;
    }
}

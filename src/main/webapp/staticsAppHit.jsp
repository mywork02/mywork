<%@ page import="com.confucian.framework.ioc.SpringContext" %>
<%@ page import="com.suime.library.service.AppHitLogService" %>
<%@ page import="com.suime.context.model.AppHitLog" %>
<%@ page import="com.confucian.framework.utils.DateUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  Logger logger = LoggerFactory.getLogger(this.getClass());
  try {
    String sourceStr = request.getParameter("source");
    AppHitLogService appHitLogService = (AppHitLogService) SpringContext.getBean("appHitLogService");
    AppHitLog appHitLog = new AppHitLog();
    Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
    appHitLog.setUpdatedAt(sqlTimestamp);
    appHitLog.setCreatedAt(sqlTimestamp);
    String userAgent = request.getHeader("User-Agent");
    appHitLog.setUserAgent(userAgent);
    appHitLog.setType("mobile");
    Byte source = 2;
    if (StringUtils.equalsIgnoreCase("A", sourceStr)) {
      source = 1;
    } else if (StringUtils.equalsIgnoreCase("C", sourceStr)) {
      source = 3;
    } else {
      source = 2;
    }
    appHitLog.setSource(source);
    if (StringUtils.contains(userAgent, "iPad") || StringUtils.contains(userAgent, "iPhone")) {
      appHitLog.setMobileType("ios");
    } else if (StringUtils.contains(userAgent, "Android")) {
      appHitLog.setMobileType("android");
    } else {
      appHitLog.setType("pc");
    }
    appHitLogService.save(appHitLog);
  } catch (Exception ex) {
    logger.error(ex.getMessage());
  }
  response.sendRedirect("http://a.app.qq.com/o/simple.jsp?pkgname=me.sui.arizona");
%>
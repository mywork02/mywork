package com.suime.library.servlet.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * url重写
 * @author ice
 */
public class UrlRewriteFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 如果session不为空，则可以浏览其他页面
		String url = this.rewriteUrl(httpRequest.getServletPath());
		httpRequest.getRequestDispatcher(url).forward(request, response);
	}

	@Override
	public void init(FilterConfig chain) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * 重写url请求
	 * @param url url 
	 * @return 重写后的url
	 */
	private String rewriteUrl(String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("/mp3Statistic?id=");
		sb.append(url.replace("/mp3/", ""));
		sb.append("");
		sb.append("");
		return sb.toString();
	}
}

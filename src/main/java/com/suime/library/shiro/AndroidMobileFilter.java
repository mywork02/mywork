package com.suime.library.shiro;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.suime.common.support.Configure;
import com.suime.context.model.support.LibraryConstants;

/**
 * Created by ice on 22/3/2016.
 */
@WebFilter(filterName = "AndroidMobileFilter", urlPatterns = { "/m2/*" })
public class AndroidMobileFilter extends AbstractMobileFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.md5Signal = Configure.getPropertyValue("http.uri.md5_validate.android");
		this.deviceTokenKey = "SUI_DeviceId";
		this.deviceType = LibraryConstants.DEVICE_TYPE_ANDROID;
	}

	@Override
	protected MyRequestWrapper generateRequestWrapper(HttpServletRequest req) throws IOException {
		Charset encoding = Charset.forName("UTF-8");
		MyRequestWrapper myRequestWrapper = new MyRequestWrapper(req, encoding);
		return myRequestWrapper;
	}

}
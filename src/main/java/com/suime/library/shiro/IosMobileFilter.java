package com.suime.library.shiro;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;

import com.suime.common.support.Configure;
import com.suime.context.model.support.LibraryConstants;

/**
 * Created by ice on 8/1/2016.
 */
@WebFilter({ "/mobile/*" })
public class IosMobileFilter extends AbstractMobileFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.md5Signal = Configure.getPropertyValue("http.uri.md5_validate.parameter");
		this.deviceTokenKey = "SUI_Udid";
		this.deviceType = LibraryConstants.DEVICE_TYPE_IOS;
	}
}

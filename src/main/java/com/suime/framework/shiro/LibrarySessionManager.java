package com.suime.framework.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.suime.framework.shiro.BaseSessionManager;
import com.suime.library.shiro.support.TokenTypeEnum;

/**
 * Created by ice on 11/3/2016.
 */
public class LibrarySessionManager extends BaseSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;

		String sessionId = "";

        if (StringUtils.isBlank(sessionId)) {
            return super.getSessionId(request, response);
        }
        String adminSuffix = TokenTypeEnum.ADMIN.getValue();
        if (StringUtils.endsWith(sessionId, adminSuffix)) {
            sessionId = sessionId.replace(adminSuffix, "");
        }
        return sessionId;
        // return super.getSessionId(request, response);
    }
}

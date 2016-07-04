package com.suime.library.shiro;

import com.suime.common.shiro.BaseSessionManager;
import com.suime.library.shiro.support.TokenTypeEnum;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Created by ice on 11/3/2016.
 */
public class LibrarySessionManager extends BaseSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;

        String sessionId = com.suime.common.shiro.BaseUserHelper.getInstance().getUserToken(req);

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

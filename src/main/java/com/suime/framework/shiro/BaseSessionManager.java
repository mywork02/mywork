package com.suime.framework.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * shiro baseSessionManager
 *
 * @author ice
 */
public class BaseSessionManager extends DefaultWebSessionManager {

    /**
     * constructor
     */
    public BaseSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String sessionId = "sessionId";
        if (StringUtils.isBlank(sessionId)) {
            return super.getSessionId(request, response);
        }
        return sessionId;
    }

}

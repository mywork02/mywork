package com.suime.common.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
        BaseCacheSessionDAO sessionDao = new BaseCacheSessionDAO();
        sessionDao.setSessionIdGenerator(new DefaultSessionIdGenerator());
        setSessionDAO(sessionDao);
        List<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new DefaultSessionListener());
        setSessionListeners(listeners);
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;

        String sessionId = BaseUserHelper.getInstance().getUserToken(req);

        if (StringUtils.isBlank(sessionId)) {
            return super.getSessionId(request, response);
        }
        return sessionId;
        // return super.getSessionId(request, response);
    }

}

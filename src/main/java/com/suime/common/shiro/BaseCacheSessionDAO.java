package com.suime.common.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseCacheSessionDAO
 * @author ice
 */
public class BaseCacheSessionDAO extends CachingSessionDAO {

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(BaseCacheSessionDAO.class);

	@Override
	protected void doDelete(Session arg0) {
	}

	@Override
	protected void doUpdate(Session arg0) {
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		logger.debug("====created session====: " + sessionId);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable arg0) {
		return null;
	}

}
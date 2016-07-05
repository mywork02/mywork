package com.suime.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * defaultSessionListener
 * @author ice
 */
public class DefaultSessionListener implements SessionListener {

	/**
	 * logger
	 */
	private static Logger logger = LoggerFactory.getLogger(DefaultSessionListener.class);

	/* (non-Javadoc)
	 * @see org.apache.shiro.session.SessionListener#onExpiration(org.apache.shiro.session.Session)
	 */
	@Override
	public void onExpiration(Session session) {
		logger.debug("session expired: " + session.getId() + ", timeout:" + session.getTimeout());
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.session.SessionListener#onStart(org.apache.shiro.session.Session)
	 */
	@Override
	public void onStart(Session session) {
		logger.debug("session start: " + session.getId());

	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.session.SessionListener#onStop(org.apache.shiro.session.Session)
	 */
	@Override
	public void onStop(Session session) {
		logger.debug("session stop: " + session.getId());
	}

}

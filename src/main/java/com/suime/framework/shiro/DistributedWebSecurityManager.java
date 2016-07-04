package com.suime.framework.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

/**
 * distributedWebSecurityManager
 *
 */
public class DistributedWebSecurityManager extends DefaultWebSecurityManager {

	/**
	 * constructor
	 */
	public DistributedWebSecurityManager() {
		super();
		// setSessionManager(new BaseSessionManager());
		// BaseSessionManager sessionManager = new BaseSessionManager();
		// sessionManager.setGlobalSessionTimeout(-1L);
		// setSessionManager(sessionManager);
//		setCacheManager(new BaseCacheManager());
//		setRememberMeManager(new BaseRememberMeManager());
	}

	@Override
	public void setRealm(Realm realm) {
		super.setRealm(realm);
	}

}

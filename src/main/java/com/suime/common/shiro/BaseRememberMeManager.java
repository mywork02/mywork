package com.suime.common.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.AbstractRememberMeManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.confucian.framework.ioc.SpringContext;
import com.suime.common.cache.CacheService;
import com.suime.common.cache.support.CacheConstantBase;

/**
 * baseRememberMeManager
 *
 * @author ice
 */
public class BaseRememberMeManager extends AbstractRememberMeManager {

    /**
     * logger
     */
	private final Logger logger = LoggerFactory.getLogger(BaseRememberMeManager.class);

    /**
     * prefix
     */
    private final String prefixRememberMe = "p_rm_";

    /**
     * cacheService
     */
    private CacheService cacheService;

    /**
     * ensureCacheInstance
     *
     * @throws CacheException
     */
    private void ensureCacheInstance() throws CacheException {
        if (cacheService != null) {
            return;
        }
        // cacheService = SpringFactory.getBean("cacheService");
        cacheService = (CacheService) SpringContext.getBean("cacheService");
        if (cacheService == null) {
            throw new CacheException("cache Instance in Shiro is null");
        }
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.mgt.RememberMeManager#forgetIdentity(org.apache.shiro.subject.SubjectContext)
     */
    @Override
    public void forgetIdentity(SubjectContext subjectContext) {
        ensureCacheInstance();
        if (cacheService != null && subjectContext.getSession() != null) {
            cacheService.removeAsync(prefixRememberMe + subjectContext.getSession().getId().toString());
        }
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.mgt.AbstractRememberMeManager#forgetIdentity(org.apache.shiro.subject.Subject)
     */
    @Override
    protected void forgetIdentity(Subject subject) {
        ensureCacheInstance();
        if (cacheService != null && subject.getSession() != null) {
            cacheService.removeAsync(prefixRememberMe + subject.getSession().getId().toString());
        }

    }

    /* (non-Javadoc)
     * @see org.apache.shiro.mgt.AbstractRememberMeManager#getRememberedSerializedIdentity(org.apache.shiro.subject.SubjectContext)
     */
    @Override
    protected byte[] getRememberedSerializedIdentity(SubjectContext subjectContext) {
        ensureCacheInstance();
        if (cacheService != null && subjectContext.getSessionId() != null) {
            logger.debug("trying to get remembered token: " + subjectContext.getSessionId());
            String base64 = (String) cacheService.get(prefixRememberMe + subjectContext.getSessionId());
            logger.debug("got the remembered token: " + base64);
            if (StringUtils.isNotBlank(base64)) {
                return Base64.decode(base64);
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.mgt.AbstractRememberMeManager#rememberSerializedIdentity(org.apache.shiro.subject.Subject, byte[])
     */
    @Override
    protected void rememberSerializedIdentity(Subject subject, byte[] serialized) {
        ensureCacheInstance();
        // base 64 encode it and store as a cookie:
        logger.debug("remember me :  " + subject.getSession().getId());
        String base64 = Base64.encodeToString(serialized);
        if (cacheService != null) {
            cacheService.set(CacheConstantBase.TIME_FOREVER, prefixRememberMe + subject.getSession().getId().toString(), base64);
            logger.debug("remember me set : " + base64);
        }
    }

}

package com.suime.library.helper;

import com.suime.common.cache.CacheService;
import com.suime.library.shiro.BaseUserHelper;
import com.suime.library.shiro.support.TokenTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * token 帮助类
 *
 * @author ice
 */
public class TokenCacheHelper {

	/**
	 * cacheService
	 */
	@Autowired
	private CacheService cacheService;

	/**
	 * 添加用户token缓存
	 */
	public void addUserToken(Long userId, String token) {
		// this.kickOut(userId);// 踢掉之前登陆的用户
		cacheService.set(BaseUserHelper.PREFIX_LOGIN_NAME_USERID + userId.toString(), BaseUserHelper.USER_CACHE_TIME, token);
		cacheService.set(BaseUserHelper.PREFIX_LOGIN_TOKEN_USERID + token, BaseUserHelper.USER_CACHE_TIME, userId.toString());
	}

	/**
	 * 添加用户token缓存
	 */
	public void addAdminToken(Long userId, String token) {
		// this.kickOut(userId);// 踢掉之前登陆的用户
		cacheService.set(BaseUserHelper.PREFIX_LOGIN_NAME_ADMINID + userId.toString(), BaseUserHelper.USER_CACHE_TIME, token);
		cacheService.set(BaseUserHelper.PREFIX_LOGIN_TOKEN_ADMINID + token, BaseUserHelper.USER_CACHE_TIME, userId.toString() + TokenTypeEnum.ADMIN.getValue());
	}

	/**
	 * 踢掉之前的token
	 * @param userId
	 */
	public void kickOut(Long userId) {
		String sid = (String) cacheService.get(BaseUserHelper.PREFIX_LOGIN_NAME_USERID + userId);
		if (StringUtils.isNotBlank(sid)) {
			Subject buildSubject = (new Subject.Builder()).sessionCreationEnabled(true).sessionId(sid).buildSubject();
			buildSubject.logout();
			cacheService.removeAsync(BaseUserHelper.PREFIX_LOGIN_NAME_USERID + sid);
		}
	}

	/**
	 * 删除管理员token
	 * @param token
	 * @return boolean
	 */
	public boolean removeAdminToken(String token) {
		if (StringUtils.isNotBlank(token)) {
			return cacheService.remove(BaseUserHelper.PREFIX_LOGIN_TOKEN_ADMINID + token);
		}
		return true;
	}
}

package com.suime.framework.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.suime.library.shiro.support.TokenTypeEnum;

/**
 * 用户权限验证
 *
 * @author ice
 */
public class SuimeAuthorizingRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			return null;
		}
		String principal = principals.toString();
		String adminSuffix = TokenTypeEnum.ADMIN.getValue();
		String userToken = "";
		if (StringUtils.isNotBlank(userToken) && StringUtils.endsWith(userToken, adminSuffix)) {
			principal = principal.replace(adminSuffix, "");
			SimpleAuthorizationInfo roleInfo = new SimpleAuthorizationInfo();
			roleInfo.addRole("123");
			return roleInfo;
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		/* 这里编写认证代码 */
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		String adminExtention = "-admin";
		// 是否管理员登录，临时方案
		if (upToken.getUsername().endsWith(adminExtention)) {
			return new SimpleAuthenticationInfo("userName_admin", "passWord", getName());
		} else {
			return new SimpleAuthenticationInfo("userName_user", "passWord", getName());
		}
	}

}

package com.suime.library.shiro;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.cache.CacheService;
import com.suime.context.model.Admin;
import com.suime.context.model.Student;
import com.suime.library.dto.AdminDto;
import com.suime.library.dto.pack.AdminBean;
import com.suime.library.dto.pack.StudentBean;
// import com.suime.library.service.AdminService;
// import com.suime.library.service.StudentService;
import com.suime.library.service.AdminService;
import com.suime.library.service.StudentService;
import com.suime.library.shiro.support.TokenTypeEnum;
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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户权限验证
 *
 * @author ice
 */
public class SuimeAuthorizingRealm extends AuthorizingRealm {

	/**
	 * studentService
	 */
	@Autowired
	private StudentService studentService;

	/**
	 * adminService
	 */
	@Autowired
	private AdminService adminService;

	/**
	 * cacheService
	 */
	@Autowired
	private CacheService cacheService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			return null;
		}
		String principal = principals.toString();
		String adminSuffix = TokenTypeEnum.ADMIN.getValue();
		String userToken = BaseUserHelper.getInstance().getUserToken();
		if (StringUtils.isNotBlank(userToken) && StringUtils.endsWith(userToken, adminSuffix)) {
			principal = principal.replace(adminSuffix, "");
			AdminDto admin = this.adminService.fetchDtoByCellphone(principal);
			if (admin == null || admin.getRoleId() == null) {
				return null;
			}
			SimpleAuthorizationInfo roleInfo = new SimpleAuthorizationInfo();
			roleInfo.addRole(admin.getRoleCode());
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
			String userName = upToken.getUsername().substring(0, upToken.getUsername().length() - adminExtention.length());
			AdminBean adminBean = new AdminBean();
			adminBean.setCellphone(userName);
			adminBean.setPassword(new String(upToken.getPassword()));
			Conds conds = new Conds();
			conds.equal("t.cellphone", adminBean.getCellphone());
			conds.equal("t.password", adminBean.getPassword());
			Admin admin = adminService.fetchSearchByConds(conds);
			if (admin != null) {
				// 账号未启用或已删除
				// if (admin.getStatus() == 1 || admin.getStatus() == 0) {
				// throw BusinessErrors.getInstance().accountInvalid();
				// }
				// //没有权限
				// if (admin.getRole() == null || admin.getRole().getId() > 2) {
				// throw BusinessErrors.getInstance().userNoAuthError();
				// }
				// CacheService cacheService = (CacheService) SpringContext.getBean("cacheService");
				cacheService.set(BaseUserHelper.PREFIX_LOGIN_NAME_USERID + admin.getCellphone(), BaseUserHelper.USER_CACHE_TIME, admin.getId());
				return new SimpleAuthenticationInfo(admin.getCellphone(), admin.getPassword(), getName());
			}
			return null;
		} else {
			StudentBean studentBean = new StudentBean();
			studentBean.setCellphone(upToken.getUsername());
			studentBean.setPassword(new String(upToken.getPassword()));
			Conds conds = new Conds();
			conds.equal("t.cellphone", studentBean.getCellphone());
			conds.equal("t.password", studentBean.getPassword());
			Student student = studentService.fetchSearchByConds(conds);
			if (student != null) {
				CacheService tempCacheService = (CacheService) SpringContext.getBean("cacheService");
				tempCacheService.set(BaseUserHelper.PREFIX_LOGIN_NAME_USERID + student.getCellphone(), BaseUserHelper.USER_CACHE_TIME, student.getId());
				return new SimpleAuthenticationInfo(student.getCellphone(), student.getPassword(), getName());
			}
			return null;
		}
	}

}

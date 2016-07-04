package com.suime.library.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.JsonUtil;
import com.confucian.framework.web.exception.ErrorCode;
import com.confucian.framework.web.exception.ExceptionUtils;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.cache.CacheService;
import com.suime.context.model.Admin;
import com.suime.library.dto.AdminDto;
import com.suime.library.helper.TokenCacheHelper;
import com.suime.library.service.AdminService;
import com.suime.library.shiro.BaseUserHelper;
import com.suime.library.shiro.support.TokenTypeEnum;

/**
 * Created by origin on 2015/12/15.
 */
@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {

	/**
	 * sid
	 */
	private static final long serialVersionUID = -5803969015537704367L;
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

	/**
	 * post
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("cellphone");
		String password = request.getParameter("password");
		if (StringUtils.isBlank(userName)) {
			BufferedReader reader = request.getReader();
			String bodyString = this.getBodyString(reader);
			JSONObject jsonObject = JsonUtil.toJsonObject(bodyString);
			logger.info("bodyString:" + bodyString);
			userName = jsonObject.getString("cellphone");
			password = jsonObject.getString("password");
		}

		response.setContentType("application/json;charset=UTF-8");
		Subject subjectLogin = SecurityUtils.getSubject();
		ThreadContext.bind(subjectLogin);
		//// 添加admin后缀用于区分管理员，临时方法
		UsernamePasswordToken token = new UsernamePasswordToken(userName + "-admin", password);
		// token.setRememberMe(true);
		try {
			subjectLogin.login(token);
			subjectLogin.getSession().setTimeout(-1);
			Subject subject = SecurityUtils.getSubject();
			// BaseUserHelper.getInstance().initAuthInfo(subject);
			ThreadLocal<String> sessionId = new ThreadLocal<String>();
			Serializable id = subject.getSession().getId();
			sessionId.set(id.toString());
			CacheService cacheService = (CacheService) SpringContext.getBean("cacheService");
			Long userId = (Long) cacheService.get(BaseUserHelper.PREFIX_LOGIN_NAME_USERID + userName);
			String sid = sessionId.get() + TokenTypeEnum.ADMIN.getValue();

			TokenCacheHelper tokenCacheHelper = (TokenCacheHelper) SpringContext.getBean("tokenCacheHelper");
			tokenCacheHelper.addAdminToken(userId, sid);

			CommonResultBean resultBean = new CommonResultBean();
			resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

			AdminService adminService = (AdminService) SpringContext.getBean("adminService");
			Conds conds = new Conds();
			conds.equal("cellphone", userName);
			conds.equal("password", password);
			Admin admin = adminService.fetchSearchByConds(conds);

			JSONObject result = new JSONObject();
			if (admin != null) {
				AdminDto adminDto = adminService.fetchDtoById(admin.getId());
				result.put("info", adminDto);
			}

			result.put("token", sid);
			resultBean.setBody(result);
			response.getWriter().write(JsonUtil.toJsonString(resultBean));
		} catch (AuthenticationException e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
			CommonResultBean errorResultBean = ExceptionUtils.generateResultBean(Constants.NORMAL_RESULT_ERROR, ErrorCode.USER_OR_PASSWORD_ERROR);
			response.getOutputStream().write(JsonUtil.toJsonString(errorResultBean).getBytes("UTF-8"));
		}

	}

	/**
	 * getHttpBody
	 *
	 * @param br
	 * @return httpBody
	 */
	public String getBodyString(BufferedReader br) {
		String inputLine;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			while ((inputLine = br.readLine()) != null) {
				stringBuffer.append(inputLine);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return stringBuffer.toString();
	}
}

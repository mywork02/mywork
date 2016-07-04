package com.suime.library.shiro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.suime.common.cache.CacheService;

/**
 * shiro 调用filter
 *
 * @author ice
 */
public class ShiroInvokeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /*
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String userToken = httpRequest.getParameter(BaseUserHelper.PARAM_TOKEN);
        if (StringUtils.isBlank(userToken)) {
            userToken = httpRequest.getHeader(BaseUserHelper.PARAM_TOKEN);
        }
        if (StringUtils.isBlank(userToken)) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(BaseUserHelper.USER_TOKEN)) {
                        userToken = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(userToken)) {

            CacheService cacheService = (CacheService) SpringContext.getBean("cacheService");
            if (StringUtils.endsWith(userToken, TokenTypeEnum.ADMIN.getValue())) {
                adminFilter(userToken, cacheService);
            } else {
                studentFilter(userToken, cacheService);
            }
        }
        */
        chain.doFilter(request, response);
    }

    /**
     * 学生
     *
     * @param userToken
     * @param cacheService
     */
    @SuppressWarnings("unused")
	private void studentFilter(String userToken, CacheService cacheService) {
//        Subject currentSubject = BaseUserHelper.getCurrentSubject(userToken);
//        BaseUserHelper.getInstance().initAuthInfo(currentSubject);
//
//        Object userIdStr = cacheService.get(BaseUserHelper.PREFIX_LOGIN_TOKEN_USERID + userToken);
//        if (userIdStr != null) {
//            StudentService studentService = (StudentService) SpringContext.getBean("studentService");
//            try {
//                Student student = studentService.fetchById(NumberUtils.toLong(userIdStr.toString()));
//                if (student != null) {
//                    Subject subjectLogin = SecurityUtils.getSubject();
//                    UsernamePasswordToken token = new UsernamePasswordToken(student.getCellphone(), student.getPassword());
//                    token.setRememberMe(true);
//                    subjectLogin.login(token);
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    /**
     * 管理员
     *
     * @param userToken
     * @param cacheService
     */
    @SuppressWarnings("unused")
	private void adminFilter(String userToken, CacheService cacheService) {
//        Subject currentSubject = BaseUserHelper.getCurrentSubject(userToken);
//        BaseUserHelper.getInstance().initAuthInfo(currentSubject);
//
//        String userIdStr = (String) cacheService.get(BaseUserHelper.PREFIX_LOGIN_TOKEN_ADMINID + userToken);
//        if (userIdStr != null) {
//            AdminService adminService = (AdminService) SpringContext.getBean("adminService");
//            try {
//                String adminSuffix = TokenTypeEnum.ADMIN.getValue();
//                if (StringUtils.endsWith(userIdStr, adminSuffix)) {
//                    userIdStr = userIdStr.replace(adminSuffix, "");
//                }
//                Admin admin = adminService.fetchById(NumberUtils.toLong(userIdStr.toString()));
//                if (admin != null) {
//                    Subject subjectLogin = SecurityUtils.getSubject();
//                    String cellphone = admin.getCellphone() + "-admin";
//                    UsernamePasswordToken token = new UsernamePasswordToken(cellphone, admin.getPassword());
//                    token.setRememberMe(true);
//                    subjectLogin.login(token);
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    @Override
    public void destroy() {

    }

}

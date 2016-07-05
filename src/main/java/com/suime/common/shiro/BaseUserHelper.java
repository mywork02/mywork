package com.suime.common.shiro;

import com.confucian.framework.ioc.SpringContext;
import com.suime.common.cache.CacheService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 用户信息工具类
 *
 * @author ice
 */
public final class BaseUserHelper {

    /**
     * 登录 token login_user_id
     */
    public static final String PREFIX_LOGIN_TOKEN_USERID = "login_token_userid_";

    /**
     * 登录用户名login_user_id
     */
    public static final String PREFIX_LOGIN_NAME_USERID = "login_name_userid_";

    /**
     * user token key
     */
    public static final String USER_TOKEN = "USER_TOKEN";

    /**
     * 参数中传递的token参数名
     */
    public static final String PARAM_TOKEN = "token";

    /**
     * admin token key
     */
    public static final String ADMIN_TOKEN = "ADMIN_TOKEN";

    /**
     * 用户缓存时间
     */
    public static final Integer USER_CACHE_TIME = 60 * 60 * 24 * 15;

    /**
     * currentUserId
     */
    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();

    /**
     * currentSessionId
     */
    private static ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * instance
     */
    private static BaseUserHelper instance = new BaseUserHelper();

    /**
     * 缓存服务
     */
    // @Autowired
    private CacheService cacheService;

    /**
     * constructor
     */
    private BaseUserHelper() {

    }

    /**
     * instance
     *
     * @return BaseUserHelper
     */
    public static BaseUserHelper getInstance() {
        return instance;
    }

    /**
     * 获取当前Subject
     *
     * @param userToken userToken
     * @return subject
     */
    public static Subject getCurrentSubject(final String userToken) {

        Subject subject = null;

        if (!StringUtils.isBlank(userToken)) {
            subject = new Subject.Builder().sessionCreationEnabled(true).sessionId(userToken).buildSubject();

        }
        if (subject == null) {
            subject = new Subject.Builder().authenticated(false).sessionCreationEnabled(true).buildSubject();

        }
        ThreadContext.bind(subject);
        return subject;
    }

    /**
     * 初始化
     *
     * @param subject
     */
    public void initAuthInfo(Subject subject) {
        if (subject != null && (subject.isAuthenticated() || subject.isRemembered())) {
            Serializable id = subject.getSession().getId();
            userId.set((Long) getCacheService().get(PREFIX_LOGIN_TOKEN_USERID + id));
            sessionId.set(id.toString());
        }
    }

    /**
     * 获取cacheService及初始化
     *
     * @return cacheService
     */
    private CacheService getCacheService() {
        if (cacheService == null) {
            cacheService = (CacheService) SpringContext.getBean("cacheService");
        }
        return cacheService;
    }

    public static Long getCurrentUserId() {
        return userId.get();
    }

    public static String getSessionId() {
        return sessionId.get();
    }

    /**
     * 根据token获取用户id
     *
     * @return 当前登录用户id
     */
    public Long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userToken = getUserToken(request);
        Long currentUserId = null;
        if (StringUtils.isNotBlank(userToken)) {
            currentUserId = (Long) getCacheService().get(PREFIX_LOGIN_TOKEN_USERID + userToken);
        }
        return currentUserId;
    }

    /**
     * 获取用户token
     *
     * @param request
     * @return userToken
     */
    public String getUserToken(HttpServletRequest request) {
        String userToken = request.getParameter(PARAM_TOKEN);
        if (StringUtils.isBlank(userToken)) {
            userToken = request.getHeader(PARAM_TOKEN);
        }
        if (StringUtils.isBlank(userToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(BaseUserHelper.USER_TOKEN)) {
                        userToken = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return userToken;
    }
}

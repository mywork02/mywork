package com.suime.library.rest.admin;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.ValidatorUtil;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Admin;
import com.suime.library.dto.pack.ResetPasswordBean;
import com.suime.library.helper.TokenCacheHelper;
import com.suime.library.service.AdminService;
import com.suime.library.shiro.BaseUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import javax.servlet.http.HttpServletResponse;

/**
 * adminRestController
 *
 * @author ice
 */
@Api(description = "管理员接口", tags = "admin")
@RestController
@RequestMapping("/admin")
public class AdminRestController extends AbstractRestController {

    /**
     * adminService
     */
    @Autowired
    private AdminService adminService;

    /**
     * tokenCacheHelper
     */
    @Autowired
    private TokenCacheHelper tokenCacheHelper;

    /**
     * 修改密码
     *
     * @param resetPasswordBean
     * @return
     */
    @ApiOperation(value = "修改密码")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public Object updatePassword(@RequestBody ResetPasswordBean resetPasswordBean) {
        if (resetPasswordBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        Long adminId = BaseUserHelper.getInstance().getAdminUserId();
        if (adminId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        Admin admin = this.adminService.fetchById(adminId);
        String password = resetPasswordBean.getPassword();
        if (!ValidatorUtil.validatePassword(password)) {
            throw BusinessErrors.getInstance().passwordError();
        }
        admin.setPassword(password);
        this.adminService.update(admin);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        return resultBean;
    }

    /**
     * 注销
     *
     * @return
     */
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "注销")
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public Object logout() {
        String token = BaseUserHelper.getInstance().getUserToken();
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.tokenCacheHelper.removeAdminToken(token));
        return resultBean;
    }

    /**
     * 获取管理员信息
     *
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取管理员信息")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    public Object info() {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Long adminId = BaseUserHelper.getInstance().getAdminUserId();
        if (adminId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        resultBean.setBody(this.adminService.fetchDtoById(adminId));
        return resultBean;
    }
}

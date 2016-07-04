package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.suime.context.model.Role;
import com.suime.library.dto.RoleModelDto;
import com.suime.library.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import java.util.ArrayList;
import java.util.List;

/**
 * roleRestController
 *
 * @author ice
 */
@Api(description = "角色接口", tags = "role")
@RestController
@RequestMapping("/role")
public class RoleRestController extends AbstractRestController {

	/**
	 * roleService
	 */
    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色
     * @return resultBean
     */
    @ApiOperation(value = "获取所有角色")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/list", method = {RequestMethod.GET})
    public Object getAll() {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        List<Role> list = this.roleService.fetchSearchByPage(null, null, 1, 0);
        if (list != null) {
            List<RoleModelDto> result = new ArrayList<>();
            for (Role role : list) {
                result.add(new RoleModelDto(role));
            }
            resultBean.setBody(result);
        }
        return resultBean;
    }

}

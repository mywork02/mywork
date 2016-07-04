package com.suime.library.rest.admin;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.RoleType;
import com.suime.context.model.Role;
import com.suime.library.dto.AdminDto;
import com.suime.library.dto.pack.AdminBean;
import com.suime.library.dto.pack.SearchAdminBean;
import com.suime.library.service.AdminService;
import com.suime.library.service.RoleService;
import com.suime.library.shiro.BaseUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * adminRestController
 *
 * @author ice
 */
@Api(description = "管理员后台", tags = "adminManagement")
@RequiresRoles(value = {RoleType.SUPER_ADMIN, RoleType.SUPER_WENKU}, logical = Logical.OR)
@RestController
@RequestMapping("/admin")
public class AdminManagementRestController extends AbstractRestController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    /**
     * 添加文库管理员
     *
     * @param adminBean
     * @return
     */
    @ApiOperation(value = "添加管理员", response = CommonResultBean.class)
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = {RequestMethod.POST})
    public Object add(@RequestBody AdminBean adminBean) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Conds roleConds = new Conds();
        roleConds.equal("code", RoleType.WENKU_ADMIN);
        Role role = this.roleService.fetchSearchByConds(roleConds);
        if (role != null) {
            Long roleId = role.getId();
            adminBean.setRoleId(roleId);
        }
        adminService.addByItem(adminBean);
        return resultBean;
    }

    /**
     * 分页获取管理员
     *
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "根据条件分页获取管理员列表", response = CommonResultBean.class)
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
                       @RequestParam(name = "text", required = false) String name,
                       @RequestParam(name = "cellphone", required = false) String cellphone,
                       @RequestParam(name = "sort", defaultValue = "DESC") String sort,
                       @RequestParam(name = "sortField", defaultValue = "createdAt") String sortField) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        SearchAdminBean searchAdminBean = new SearchAdminBean();
        searchAdminBean.setSort(OrderType.valueOf(sort));
        searchAdminBean.setSortField(sortField);
        searchAdminBean.setCellphone(cellphone);
        searchAdminBean.setName(name);
        Long adminId = BaseUserHelper.getInstance().getAdminUserId();
        AdminDto adminDto = this.adminService.fetchDtoById(adminId);
        if (!RoleType.SUPER_ADMIN.equals(adminDto.getRoleCode())) {
            searchAdminBean.setRoleCode(RoleType.WENKU_ADMIN);
        }
        resultBean.setBody(this.adminService.pageByItem(searchAdminBean, page, pageSize));
        return resultBean;
    }

    /**
     * 更改角色
     *
     * @param adminBean
     * @return
     */
    @ApiOperation(value = "更改管理员角色", response = CommonResultBean.class)
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/changeRole", method = {RequestMethod.POST})
    public Object changeRole(@RequestBody AdminBean adminBean) {
        if (adminBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        adminBean.setName(null);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        this.adminService.updateByItem(adminBean);
        return resultBean;
    }

    /**
     * 删除账户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除账户")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/delete/{id}", method = {RequestMethod.DELETE})
    public Object remove(@PathVariable("id") Long id) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Byte status = 0;
        this.adminService.removeOrUpdateById(status, id);
        return resultBean;
    }

    /**
     * 启用账户
     *
     * @param adminBean
     * @return
     */
    @ApiOperation(value = "启用账户")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/startUsing", method = {RequestMethod.POST})
    public Object startUsing(@RequestBody AdminBean adminBean) {
        if (adminBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Byte status = 2;
        this.adminService.removeOrUpdateById(status, adminBean.getId());
        return resultBean;
    }

    /**
     * 冻结账户
     *
     * @param adminBean
     * @return
     */
    @ApiOperation(value = "冻结账户")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/stopUsing", method = {RequestMethod.POST})
    public Object stopUsing(@RequestBody AdminBean adminBean) {
        if (adminBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Byte status = 1;
        this.adminService.removeOrUpdateById(status, adminBean.getId());
        return resultBean;
    }
}

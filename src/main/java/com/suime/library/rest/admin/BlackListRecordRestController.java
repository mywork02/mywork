package com.suime.library.rest.admin;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.mybatis.support.scope.OrderType;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.RoleType;
import com.suime.context.model.support.StudentStatusEnum;
import com.suime.library.dto.pack.SearchAdminBean;
import com.suime.library.dto.pack.StudentBean;
import com.suime.library.service.BlackListRecordService;
import com.suime.library.service.StudentService;
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

import com.confucian.framework.web.AbstractRestController;

/**
 * blackListRecordRestController
 *
 * @author ice
 */
@Api(description = "黑名单接口", tags = "blackList")
@RequiresRoles(value = {RoleType.SUPER_ADMIN, RoleType.SUPER_WENKU, RoleType.WENKU_ADMIN}, logical = Logical.OR)
@RestController
@RequestMapping("/blackList")
public class BlackListRecordRestController extends AbstractRestController {

    @Autowired
    private StudentService studentService;

    /**
     * blackListRecordService
     */
    @Autowired
    private BlackListRecordService blackListRecordService;

    /**
     * 添加到黑名单
     *
     * @param studentBean
     * @return resultBean
     */
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = {RequestMethod.POST})
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "添加到黑名单")
    public Object addBlackList(@RequestBody StudentBean studentBean) {
        if (studentBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        Long adminId = BaseUserHelper.getInstance().getAdminUserId();
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        this.blackListRecordService.addToBlackList(studentBean.getId(), adminId);
        return resultBean;
    }

    /**
     * 移除黑名单
     *
     * @param id
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/remove/{id}", method = {RequestMethod.DELETE})
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "移除黑名单")
    public Object removeBlackList(@PathVariable("id") Long id) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Long adminId = BaseUserHelper.getInstance().getAdminUserId();
        this.blackListRecordService.removeFromBlackList(id, adminId);
        return resultBean;
    }

    /**
     * 分页获取黑名单
     *
     * @param studentNickName
     * @param cellphone
     * @param sortField
     * @param sort
     * @param curPage
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页获取黑名单")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam(name = "text", required = false) String studentNickName,
                       @RequestParam(name = "sortField", required = false) String sortField,
                       @RequestParam(name = "sort", defaultValue = "DESC") String sort,
                       @RequestParam(name = "page", defaultValue = "1") Integer curPage,
                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
                       @RequestParam(name = "cellphone", required = false) String cellphone) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        SearchAdminBean searchAdminBean = new SearchAdminBean();
        searchAdminBean.setName(studentNickName);
        searchAdminBean.setCellphone(cellphone);
        searchAdminBean.setSort(OrderType.valueOf(sort));
        searchAdminBean.setSortField(sortField);
        searchAdminBean.setStudentStatus(StudentStatusEnum.WENKU_BLACK);
        resultBean.setBody(this.studentService.searchByItem(searchAdminBean, curPage, pageSize));
        return resultBean;
    }

}

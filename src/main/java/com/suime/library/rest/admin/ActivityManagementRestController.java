package com.suime.library.rest.admin;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.StringUtils;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.constants.RoleType;
import com.suime.context.model.Activity;
import com.suime.library.dto.pack.ActivityBean;
import com.suime.library.error.ActivityErrors;
import com.suime.library.service.ActivityService;
import com.suime.library.service.support.OrderFieldEnum;
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

import java.sql.Timestamp;

/**
 * activityManagementRestController
 *
 * @author ice
 */
@Api(description = "活动后台接口", tags = "activityManagement")
@RequiresRoles(value = {RoleType.SUPER_ADMIN, RoleType.SUPER_WENKU, RoleType.WENKU_ADMIN}, logical = Logical.OR)
@RestController
@RequestMapping("/activity")
public class ActivityManagementRestController extends AbstractRestController {

    @Autowired
    private ActivityService activityService;

    /**
     * 添加
     *
     * @param activityBean
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加活动", response = CommonResultBean.class)
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    public Object add(@RequestBody ActivityBean activityBean) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        this.activityService.addByItem(activityBean);
        return resultBean;
    }

    /**
     * 更新
     *
     * @param activityBean
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动", response = CommonResultBean.class)
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    public Object update(@RequestBody ActivityBean activityBean) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        this.activityService.updateByItem(activityBean);
        return resultBean;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/remove/{id}", method = RequestMethod.DELETE)
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "删除活动", response = CommonResultBean.class)
    public Object remove(@PathVariable("id") Long id) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

        Activity activity = this.activityService.fetchById(id);
        if (activity == null) {
            throw ActivityErrors.getInstance().activityNotFoundError(id);
        }
        Byte actived = 0;
        Timestamp currenTime = DateUtil.getSqlTimestamp();
        activity.setActived(actived);
        activity.setUpdatedAt(currenTime);
        this.activityService.update(activity);
        return resultBean;
    }

    /**
     * 根据条件分页获取
     *
     * @param code
     * @param sort
     * @param sortField
     * @param page
     * @param pageSize
     * @return
     */
    @RequiresAuthentication
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "根据条件分页获取活动", response = CommonResultBean.class)
    public Object list(@RequestParam(name = "text", required = false) String code,
                       @RequestParam(name = "sort", defaultValue = "DESC") String sort,
                       @RequestParam(name = "sortField", defaultValue = "createdAt") String sortField,
                       @RequestParam(name = "page", defaultValue = "1") Integer page,
                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        Conds conds = new Conds();
        if (StringUtils.isNotBlank(code)) {
            conds.like("code", code);
        }
        conds.equal("actived", 1);
        OrderType orderType = OrderType.valueOf(sort);
        if (orderType == null) {
            orderType = OrderType.DESC;
        }
        String tempSortField;
        if (StringUtils.isBlank(sortField) || StringUtils.equals(sortField, "createdAt")) {
            tempSortField = "created_at";
        } else {
            tempSortField = OrderFieldEnum.READ_COUNT.getDbField();
        }
        Sort tempSort = new Sort(tempSortField, orderType);

        resultBean.setBody(this.activityService.fetchDtoPageSearchByPage(conds, tempSort, page, pageSize));
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        return resultBean;
    }
}

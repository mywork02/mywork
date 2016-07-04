package com.suime.library.rest.personal;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.suime.common.error.BusinessErrors;
import com.suime.library.dto.PrintTaskDto;
import com.suime.library.service.PrintTaskService;
import com.suime.library.shiro.BaseUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * cartRestController
 *
 * @author ice
 */
@Api(description = "购物车接口", tags = "cart")
@RestController
@RequestMapping("/cart")
public class CartRestController extends AbstractRestController {

    /**
     * printTaskService
     */
    @Autowired
    private PrintTaskService printTaskService;

    /**
     * 添加购物车
     *
     * @param id
     * @return resultBean
     */
    @ApiOperation(value = "添加到购物车")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/add/{id}", method = {RequestMethod.POST})
    public Object add(@PathVariable("id") Long id) {
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        PrintTaskDto printTaskDto = this.printTaskService.addToCart(userId, id);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(printTaskDto);
        return resultBean;
    }

    /**
     * 获取购物车中的数量
     *
     * @return resultBean
     */
    @ApiOperation(value = "获取购物车中的数量")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequestMapping(path = "/countInCart", method = {RequestMethod.GET})
    public Object countInCart() {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId != null) {
            resultBean.setBody(this.printTaskService.countInCart(userId));
        }
        return resultBean;
    }
}

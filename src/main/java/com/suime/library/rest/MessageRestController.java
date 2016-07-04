package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Message;
import com.suime.library.dto.TabBadgeDto;
import com.suime.library.dto.pack.PushMessageBean;
import com.suime.library.helper.XingePushHelper;
import com.suime.library.service.MessageService;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

/**
 * messageRestController
 *
 * @author ice
 */
@Api(description = "消息中心", tags = "message")
@RestController
@RequestMapping("/message")
public class MessageRestController extends AbstractRestController {

    /**
     * messageService
     */
    @Autowired
    private MessageService messageService;

    /**
     * 获取自己的消息列表
     *
     * @param page     currentPage
     * @param pageSize pageSize
     * @return
     */
    @ApiOperation(value = "获取自己的消息列表")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/list", method = {RequestMethod.GET})
    public Object list(@RequestParam(name = "page", defaultValue = "0") Integer page,
                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Conds conds = new Conds();
        conds.equal("receiver_id", userId);
        Page result = this.messageService.fetchDtoPageSearch(conds, null, page, pageSize);
        resultBean.setBody(result);
        return resultBean;
    }

    /**
     * 获取自己的打印及收藏消息列表
     *
     * @param page     currentPage
     * @param pageSize pageSize
     * @return
     */
    @ApiOperation(value = "获取自己的打印及收藏消息列表")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/printlist", method = {RequestMethod.GET})
    public Object print(@RequestParam(name = "page", defaultValue = "0") Integer page,
                        @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Conds conds = new Conds();
        conds.equal("receiver_id", userId);
        Object[] object = new Object[2];
        object[0] = "1";
        object[1] = "3";
        conds.in("message_type", object);
        Page result = this.messageService.fetchDtoPageSearch(conds, null, page, pageSize);
        resultBean.setBody(result);
        return resultBean;
    }

    /**
     * 获取自己的评论及回复消息列表
     *
     * @param page     currentPage
     * @param pageSize pageSize
     * @return
     */
    @ApiOperation(value = "获取自己的评论及回复消息列表")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/commentlist", method = {RequestMethod.GET})
    public Object commentlist(@RequestParam(name = "page", defaultValue = "0") Integer page,
                              @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Conds conds = new Conds();
        conds.equal("receiver_id", userId);
        Object[] object = new Object[2];
        object[0] = "6";
        object[1] = "7";
        conds.in("message_type", object);
        Page result = this.messageService.fetchDtoPageSearch(conds, null, page, pageSize);
        resultBean.setBody(result);
        return resultBean;
    }

    /**
     * 获取所有未读消息数量
     *
     * @return
     */
    @ApiOperation(value = "添加收藏记录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/unreadCount", method = {RequestMethod.GET})
    public Object count() {
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        Conds conds = new Conds();
        conds.equal("receiver_id", userId);
        Byte bZero = 0;
        Byte bOne = 1;
        conds.equal("is_readed", bZero);
        conds.equal("actived", bOne);
        Integer count = this.messageService.count(conds);

        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(count);
        return resultBean;
    }

    /**
     * tabBadge, ios 使用tab消息数
     *
     * @return
     */
    @ApiOperation(value = "tabBadge")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/tabBadge", method = {RequestMethod.GET})
    public Object tabBadge() {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

        Long userId = BaseUserHelper.getInstance().getUserId();
        TabBadgeDto tabBadgeDto = new TabBadgeDto();
        if (userId != null) {
            Conds conds = new Conds();
            conds.equal("receiver_id", userId);
            Byte bZero = 0;
            Byte bOne = 1;
            conds.equal("is_readed", bZero);
            conds.equal("actived", bOne);
            Integer count = this.messageService.count(conds);
            tabBadgeDto.setMessage(count);
        }
        resultBean.setBody(tabBadgeDto);
        return resultBean;
    }

    /**
     * 标记该消息为已读记录
     *
     * @return
     */
    @ApiOperation(value = "标记该消息为已读记录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/read/{id}", method = {RequestMethod.POST})
    public Object read(@PathVariable("id") Long id) {
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        Message message = this.messageService.fetchById(id);
        Long receiverId = message.getReceiverId();
        if (!userId.equals(receiverId)) {
            throw BusinessErrors.getInstance().userNoAuthError();
        }
        Byte readed = 1;//1:已阅读
        message.setIsReaded(readed);
        this.messageService.update(message);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        return resultBean;
    }

    /**
     * 标记该消息为已读记录
     *
     * @return
     */
    @ApiOperation(value = "标记该消息为已读记录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/readAll", method = {RequestMethod.POST})
    public Object readAll() {
        Long userId = BaseUserHelper.getInstance().getUserId();
        if (userId == null) {
            throw BusinessErrors.getInstance().userOfflineError();
        }
        Integer itemCount = this.messageService.update2ReadedByReceiverId(userId);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(itemCount);
        return resultBean;
    }


    /**
     * 测试推送接口
     *
     * @return
     */
    @ApiOperation(value = "测试推送接口")
    @ApiImplicitParam()
    @RequestMapping(path = "/testIOSPush", method = {RequestMethod.POST})
    public Object testIOSPush(@RequestBody PushMessageBean pushMessageBean) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        XingePushHelper.pushIos(pushMessageBean);
        return resultBean;
    }
}

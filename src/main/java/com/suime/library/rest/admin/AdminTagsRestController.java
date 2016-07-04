package com.suime.library.rest.admin;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.context.model.Tags;
import com.suime.library.dto.TagsDto;
import com.suime.library.dto.pack.TagsBean;
import com.suime.library.error.TagsErrors;
import com.suime.library.service.TagsService;
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

import java.util.List;

/**
 * tagsRestController
 *
 * @author ice
 */
@Api(description = "管理员标签接口", tags = "tags")
@RestController
@RequestMapping("/tags/admin")
public class AdminTagsRestController extends AbstractRestController {

    /**
     * tagsService
     */
    @Autowired
    private TagsService tagsService;

    /**
     * 添加标签（管理员）
     *
     * @return resultBean
     */
    @ApiOperation(value = "添加标签（管理员）")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = {RequestMethod.POST})
    public Object addForAdmin(@RequestBody TagsBean tagsBean) {
        Byte sourceOfAdmin = (byte) 1;
        Long adminId = BaseUserHelper.getInstance().getAdminUserId();
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.tagsService.add(tagsBean, sourceOfAdmin, adminId));
        return resultBean;
    }

    /**
     * 修改标签（管理员）
     *
     * @return resultBean
     */
    @ApiOperation(value = "修改标签（管理员）")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/update", method = {RequestMethod.POST})
    public Object updateForAdmin(@RequestBody TagsBean tagsBean) {
        Byte sourceOfAdmin = (byte) 1;
        Long adminId = BaseUserHelper.getInstance().getUserId();
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.tagsService.update(tagsBean, sourceOfAdmin, adminId));
        return resultBean;
    }
}

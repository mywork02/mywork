package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.suime.common.error.BusinessErrors;
import com.suime.library.dto.RatingRecordDto;
import com.suime.library.dto.pack.RatingRecordBean;
import com.suime.library.service.RatingRecordService;
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

/**
 * ratingRecordRestController
 * @author ice
 */
@Api(description = "评分接口", tags = "rating")
@RestController
@RequestMapping("/rating")
public class RatingRecordRestController extends AbstractRestController {

    /**
     * ratingRecordService
     */
    @Autowired
    private RatingRecordService ratingRecordService;

    /**
     * add
     *
     * @return resultBean
     */
    @ApiOperation(value = "添加评分记录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = {RequestMethod.POST})
    public Object add(@RequestBody RatingRecordBean ratingRecordBean) {
        if (ratingRecordBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Long studentId = BaseUserHelper.getInstance().getUserId();
        ratingRecordBean.setStudentId(studentId);
        RatingRecordDto ratingRecordDto = ratingRecordService.addRatingRecord(ratingRecordBean);
        resultBean.setBody(ratingRecordDto);
        return resultBean;
    }

}

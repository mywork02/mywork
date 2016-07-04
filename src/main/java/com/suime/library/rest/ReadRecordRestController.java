package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.suime.common.error.BusinessErrors;
import com.suime.library.dto.ReadRecordDto;
import com.suime.library.dto.pack.ReadRecordBean;
import com.suime.library.service.ReadRecordService;
import com.suime.library.shiro.BaseUserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import javax.servlet.http.HttpServletRequest;

/**
 * readRecordRestController
 *
 * @author ice
 */
@Api(description = "阅读记录接口", tags = "readRecord")
@RestController
@RequestMapping("/readRecord")
public class ReadRecordRestController extends AbstractRestController {

    /**
     * readRecordService
     */
    @Autowired
    private ReadRecordService readRecordService;
    /**
     * 添加访问记录
     *
     * @param readRecordBean
     * @return resultBean
     */
    @ApiOperation(value = "添加阅读记录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequestMapping(path = "/add", method = {RequestMethod.POST})
    public Object add(@RequestBody ReadRecordBean readRecordBean) {
        if (readRecordBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        Long studentId = BaseUserHelper.getInstance().getUserId();
        readRecordBean.setStudentId(studentId);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        ReadRecordDto readRecordDto = readRecordService.addReadRecord(readRecordBean);
        if (readRecordDto != null) {
            resultBean.setBody(readRecordDto);
        } else {
            resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
        }
        return resultBean;
    }

    /**
     * 查询文档访问记录
     *
     * @param page
     * @return resultBean
     */
    @ApiOperation(value = "查询文档阅读记录")
    @RequestMapping(path = "/page", method = {RequestMethod.GET})
    public Object page(@RequestParam("docId") Long docId, @RequestParam("page") Integer page, HttpServletRequest request) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

        ReadRecordBean readRecordBean = new ReadRecordBean();
        readRecordBean.setStudentDocumentId(docId);
        readRecordBean.setActived((byte) 1);
        Long studentId = NumberUtils.toLong(request.getParameter("studentId"), 0);
        if (studentId > 0) {
            readRecordBean.setStudentId(studentId);
        }
        int pageSize = NumberUtils.toInt(request.getParameter("pageSize"), Constants.VALUE_PAGE_SIZE);
        resultBean.setBody(this.readRecordService.pageByItem(readRecordBean, page, pageSize));
        return resultBean;
    }

}

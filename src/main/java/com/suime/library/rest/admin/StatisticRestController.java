package com.suime.library.rest.admin;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.suime.constants.RoleType;
import com.suime.library.service.CategoryService;
import com.suime.library.service.StudentDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by origin on 2015/12/29.
 */
@Api(description = "文档统计数据接口", tags = "stdocStatistic")
@RequiresRoles(value = {RoleType.SUPER_ADMIN, RoleType.SUPER_WENKU, RoleType.WENKU_ADMIN}, logical = Logical.OR)
@RestController
@RequestMapping("/stdocStatistic")
public class StatisticRestController extends AbstractRestController {

    /**
     * studentDocumentService
     */
    @Autowired
    private StudentDocumentService studentDocumentService;

    /**
     * categoryService
     */
    @Autowired
    public CategoryService categoryService;

    /**
     * 各分类文档数
     *
     * @param page
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "各分类文档数")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/categoryStdoc", method = RequestMethod.GET)
    public Object categoryList(@RequestParam(name = "page", defaultValue = "1") Integer page,
                               @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.categoryService.statisticCountByCategory(page, pageSize));
        return resultBean;
    }


}

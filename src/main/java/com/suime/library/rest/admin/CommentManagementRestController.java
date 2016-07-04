/**
 * commentManagementRestController.java
 * 2015年12月3日
 */
package com.suime.library.rest.admin;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.suime.constants.RoleType;
import com.suime.library.dto.pack.CommentBean;
import com.suime.library.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * commentManagementRestController
 *
 * @author origin
 */
@Api(description = "评论管理接口", tags = "commentManagement")
@RequiresRoles(value = {RoleType.SUPER_ADMIN, RoleType.SUPER_WENKU, RoleType.WENKU_ADMIN}, logical = Logical.OR)
@RestController
@RequestMapping("/commentManagement")
public class CommentManagementRestController extends AbstractRestController {

    @Autowired
    private CommentService commentService;

    /**
     * 删除单个评论
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除单个评论")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/remove", method = {RequestMethod.DELETE})
    public Object remove(@RequestParam("id") Long id) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        this.commentService.removeById(id);
        return resultBean;
    }

    /**
     * 批量删除评论
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "批量删除评论")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/removeList", method = {RequestMethod.DELETE})
    public Object removeList(@RequestParam(name = "ids") List<Long> ids) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        this.commentService.removeByIdList(ids);
        return resultBean;
    }

    /**
     * 根据手机号、内容获取评论
     *
     * @param page
     * @param pageSize
     * @param cellphone,非必需
     * @param text          搜索内容,非必需
     * @return
     */
    @ApiOperation(value = "根据手机号或内容获取评论")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/search", method = {RequestMethod.GET})
    public Object pageList(@RequestParam(name = "page", defaultValue = "0") Integer page,
                           @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                           @RequestParam(name = "cellphone", required = false) String cellphone,
                           @RequestParam(name = "text", required = false) String text) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.commentService.pageByStudentOrContent(cellphone, text, page, pageSize));
        return resultBean;
    }

    /**
     * 根据studentDocumentId获取评论
     *
     * @param page
     * @param pageSize
     * @param studentDocumentId
     * @return
     */
    @ApiOperation(value = "根据studentDocumentId获取评论")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/list/{studentDocumentId}", method = {RequestMethod.GET})
    public Object pageByItem(@RequestParam(name = "page", defaultValue = "0") Integer page,
                             @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize,
                             @PathVariable("studentDocumentId") Long studentDocumentId) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        CommentBean commentBean = new CommentBean();
        commentBean.setStudentDocumentId(studentDocumentId);
        resultBean.setBody(this.commentService.pageByItem(commentBean, page, pageSize));
        return resultBean;
    }
}

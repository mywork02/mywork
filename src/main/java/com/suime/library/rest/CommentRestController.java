package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.suime.common.error.BusinessErrors;
import com.suime.library.dto.CommentDto;
import com.suime.library.dto.pack.CommentBean;
import com.suime.library.service.CommentService;
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
 * commentRestController
 *
 * @author ice
 */
@Api(description = "评论接口", tags = "comment")
@RestController
@RequestMapping("/comment")
public class CommentRestController extends AbstractRestController {

    /**
     * doc
     */
    @Autowired
    private CommentService commentService;

    /**
     * add
     *
     * @param docCommentBean
     * @return resultBean
     */
    @ApiOperation(value = "添加评论")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = {RequestMethod.POST})
    public Object add(@RequestBody CommentBean docCommentBean) {
        if (docCommentBean == null) {
            throw BusinessErrors.getInstance().paramsError();
        }
        Long studentId = BaseUserHelper.getInstance().getUserId();
        docCommentBean.setStudentId(studentId);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        CommentDto docCommentDto = commentService.addComment(docCommentBean);
        resultBean.setBody(docCommentDto);
        return resultBean;
    }

    /**
     * 文档评论记录分页查询
     *
     * @param page
     * @return resultBean
     */
    @ApiOperation(value = "文档评论记录分页查询")
    @RequestMapping(path = "/page/{docId}/{page}", method = {RequestMethod.GET})
    public Object page(@PathVariable("docId") Long docId, @PathVariable("page") Integer page) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

        CommentBean docCommentBean = new CommentBean();
        Byte actived = 1;
        docCommentBean.setActived(actived);
        Byte reviewState = 2;
        docCommentBean.setStudentDocumentId(docId);
        int pageSize = Constants.VALUE_PAGE_SIZE;
        resultBean.setBody(this.commentService.pageByItem(docCommentBean, page, pageSize));
        return resultBean;
    }

    /**
     * 移动端文档评论记录分页查询
     *
     * @param page
     * @return resultBean
     */
    @ApiOperation(value = "移动端文档评论记录分页查询")
    @RequestMapping(path = "/mobile/page/{docId}", method = {RequestMethod.GET})
    public Object pageForMobile(@PathVariable("docId") Long docId, @RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

        CommentBean docCommentBean = new CommentBean();
        Byte actived = 1;
        docCommentBean.setActived(actived);
        docCommentBean.setStudentDocumentId(docId);
        Byte reviewState = 2;
        docCommentBean.setReviewState(reviewState);
        resultBean.setBody(this.commentService.pageByItemForMobile(docCommentBean, page, pageSize));
        return resultBean;
    }

}

package com.suime.library.rest.v2;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.suime.common.error.BusinessErrors;
import com.suime.library.dto.CommentDto;
import com.suime.library.dto.PointResultDto;
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

/**
 * commentRestController
 *
 * @author ice
 */
@Api(description = "评论接口", tags = "comment")
@RestController
@RequestMapping("/v2/comment")
public class CommentV2RestController extends AbstractRestController {

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
    @ApiOperation(value = "添加评论v2 版本,返回相应的获取积分的信息")
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
        PointResultDto pointResultDto = new PointResultDto();
        pointResultDto.setPoint(docCommentDto.getPoint());
        docCommentDto.setPoint(null);
        pointResultDto.setComment(docCommentDto);
        resultBean.setBody(pointResultDto);
        return resultBean;
    }


}

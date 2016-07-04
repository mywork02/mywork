package com.suime.library.rest;

/**
 * Created by origin on 2015/12/25.
 */

import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractController;
import com.suime.common.dto.pack.AliyunOSSBean;
import com.suime.common.error.BusinessErrors;
import com.suime.common.helper.ImageAliyunOSSHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.UUID;

/**
 * 上传图片(开放)接口
 *
 * @author ice
 */
@Api(description = "上传图片接口", tags = "img")
@RestController
@RequestMapping(path = "/img")
public class ImageRestController extends AbstractController {

    /**
     * 获取图片上传地址
     *
     * @param request request
     * @return 文件上传地址
     */
    @ApiOperation(value = "获取图片上传地址")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequestMapping(path = "/uploadKey", method = RequestMethod.GET)
    public Object beginCreate(HttpServletRequest request) {
        String string = UUID.randomUUID().toString();
        String imageKey = "image-" + string;
        final int minutes = 20; // 20分钟
        URL uploadUrl = ImageAliyunOSSHelper.getInstance().generatePutPresignedUrl(imageKey, minutes);

        JSONObject resultFont = new JSONObject();
        resultFont.put("imageKey", imageKey);
        resultFont.put("url", uploadUrl.toString());

        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(resultFont);
        return resultBean;
    }

    /**
     * 获取图片访问地址
     *
     * @param imageKey
     * @return 图片访问地址
     */
    @ApiOperation(value = "获取图片访问地址")
    @RequestMapping(path = "/getVisitUrl", method = {RequestMethod.GET})
    public Object endCreate(@RequestParam("imageKey") String imageKey) {
        String extension = "img";
        if (StringUtils.isEmpty(imageKey)) {
            throw BusinessErrors.getInstance().paramsError();
        }

        ImageAliyunOSSHelper aliyunOSSClient = ImageAliyunOSSHelper.getInstance();
        AliyunOSSBean aliyunOSSBean = aliyunOSSClient.getObjectFromOSSByKey(imageKey, extension);
        if (aliyunOSSBean == null) {
            throw BusinessErrors.getInstance().fileFetchFailure();
        }
        String destinationKey = aliyunOSSBean.toString();
        String contentType = "image/jpeg";
        aliyunOSSClient.copyObject(imageKey, destinationKey, contentType);
        aliyunOSSClient.deleteObject(imageKey);

        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(aliyunOSSClient.getVisitUrl(destinationKey));
        return resultBean;

    }
}

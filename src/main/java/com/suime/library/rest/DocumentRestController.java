package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.JsonUtil;
import com.suime.common.dto.MnsMessageDto;
import com.suime.common.dto.pack.AliyunOSSBean;
import com.suime.common.dto.pack.FileBean;
import com.suime.common.error.BusinessErrors;
import com.suime.common.helper.AliyunMNSClientHelper;
import com.suime.common.helper.AliyunOSSClientHelper;
import com.suime.common.helper.MnsQueues;
import com.suime.common.util.FileTypeUtil;
import com.suime.context.model.Document;
import com.suime.context.model.File;
import com.suime.library.dto.DocumentDto;
import com.suime.library.dto.pack.DocFileBean;
import com.suime.library.service.DocumentService;
import com.suime.library.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * documentRestController
 *
 * @author ice
 */
@Api(description = "文件接口", tags = "document")
@RestController
@RequestMapping("/document")
public class DocumentRestController extends AbstractRestController {

    /**
     * documentService
     */
    @Autowired
    private DocumentService documentService;

    /**
     * fileService
     */
    @Autowired
    private FileService fileService;

    /**
     * 获取文件上传地址
     *
     * @param request request
     * @return 文件上传地址
     */
    @ApiOperation(value = "获取文件上传地址")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/beginCreate", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object beginCreate(HttpServletRequest request) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        String uploadKey = "upload-" + UUID.randomUUID().toString();
        final int minutes = 20; // 20分钟
        URL url = AliyunOSSClientHelper.getInstance().generatePutPresignedUrl(uploadKey, minutes);

        String urlString = url.toString();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", uploadKey);
        map.put("url", urlString);
        // map.put("token", token);
        resultBean.setBody(map);
        return resultBean;
    }

    /**
     * endCreate 结束上传文件
     *
     * @param request
     * @return 结束上传文件
     */
    @ApiOperation(value = "结束文件上传")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    @RequiresAuthentication
    @RequestMapping(path = "/endCreate", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object endCreate(HttpServletRequest request, @RequestBody DocFileBean docFileBean) {

        FileBean fileBean = getFileBean(request, docFileBean);

        DocumentDto documentDto = this.documentService.addDocument(fileBean);
        if (documentDto != null) {
            MnsMessageDto messageDto = new MnsMessageDto();
            MnsMessageDto convertMessageDto = new MnsMessageDto();
            messageDto.setId(documentDto.getFileId());
            messageDto.setKey(documentDto.getFileKey());
            messageDto.setType(MnsMessageDto.GET_PAGE_NUMBER);
            String messageBoby = JsonUtil.toJsonString(messageDto);
            AliyunMNSClientHelper mnsClient = AliyunMNSClientHelper.getInstance();

            convertMessageDto.setHandouts("X11");
            convertMessageDto.setId(documentDto.getFileId());
            convertMessageDto.setKey(documentDto.getFileKey());
            convertMessageDto.setType(MnsMessageDto.CONVERT_TO_PDF);
            Byte needPreview = 1;
            convertMessageDto.setNdpre(needPreview);

            String extension = fileBean.getExtension();
            if (StringUtils.startsWithIgnoreCase(extension, "ppt")) {
                mnsClient.messageSend(MnsQueues.COUNT_PAGE_PPT, messageBoby);
                mnsClient.messageSend(MnsQueues.TRANSFORM_PPT, JsonUtil.toJsonString(convertMessageDto));
                mnsClient.messageSend(MnsQueues.TRANSFORM_PPT_COM, JsonUtil.toJsonString(convertMessageDto));
            } else {
                mnsClient.messageSend(MnsQueues.COUNT_PAGE, messageBoby);
                mnsClient.messageSend(MnsQueues.TRANSFORM, JsonUtil.toJsonString(convertMessageDto));
                if (StringUtils.startsWithIgnoreCase(extension, "doc")) {
                    mnsClient.messageSend(MnsQueues.TRANSFORM_DOC, JsonUtil.toJsonString(convertMessageDto));
                }
            }
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(documentDto);
        return resultBean;
    }


    /**
     * endCreate 结束上传文件
     *
     * @param request
     * @return 结束上传文件
     */
    @RequiresAuthentication
    @RequestMapping(path = "/endCreateByPrint", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object endCreateByPrint(HttpServletRequest request, @RequestBody DocFileBean docFileBean) {

        FileBean fileBean = getFileBean(request, docFileBean);

        DocumentDto documentDto = this.documentService.addDocument(fileBean);
        if (documentDto != null) {
            MnsMessageDto messageDto = new MnsMessageDto();
            MnsMessageDto convertMessageDto = new MnsMessageDto();
            messageDto.setId(documentDto.getFileId());
            messageDto.setKey(documentDto.getFileKey());
            messageDto.setType(MnsMessageDto.GET_PAGE_NUMBER);
            String messageBoby = JsonUtil.toJsonString(messageDto);
            AliyunMNSClientHelper mnsClient = AliyunMNSClientHelper.getInstance();

            convertMessageDto.setHandouts("X11");
            convertMessageDto.setId(documentDto.getFileId());
            convertMessageDto.setKey(documentDto.getFileKey());
            convertMessageDto.setType(MnsMessageDto.CONVERT_TO_PDF);
            Byte needPreview = 1;
            convertMessageDto.setNdpre(needPreview);

            if (StringUtils.startsWithIgnoreCase(fileBean.getExtension(), "ppt")) {
                mnsClient.messageSend(MnsQueues.COUNT_PAGE_PPT, messageBoby);
                mnsClient.messageSend(MnsQueues.TRANSFORM_PPT, JsonUtil.toJsonString(convertMessageDto));
                mnsClient.messageSend(MnsQueues.TRANSFORM_PPT_COM, JsonUtil.toJsonString(convertMessageDto));
            } else {
                mnsClient.messageSend(MnsQueues.COUNT_PAGE, messageBoby);
                if (StringUtils.startsWithIgnoreCase(fileBean.getExtension(), "doc")) {
                    mnsClient.messageSend(MnsQueues.TRANSFORM_PRINTER, JsonUtil.toJsonString(convertMessageDto));
                } else {
                    mnsClient.messageSend(MnsQueues.TRANSFORM, JsonUtil.toJsonString(convertMessageDto));
                }
            }
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(documentDto);
        return resultBean;
    }


    private FileBean getFileBean(HttpServletRequest request, @RequestBody DocFileBean docFileBean) {
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            token = docFileBean.getToken();
        }
        BusinessErrors businessErrors = BusinessErrors.getInstance();
        if (docFileBean == null) {
            throw businessErrors.paramsError();
        }
        // UserBean userBean = TokenHelper.getInstance().getUserBeanByToken(token);
        String fileName = docFileBean.getFileName().trim();
        String key = docFileBean.getKey();
        check(businessErrors, fileName, key);

        AliyunOSSClientHelper aliyunOSSClient = AliyunOSSClientHelper.getInstance();

        //移动文件到一个临时的地址，使文件不能被改动
        String tempKey = UUID.randomUUID().toString();
        aliyunOSSClient.copyObject(key, tempKey, null);
        try {
            aliyunOSSClient.deleteObject(key);
        } catch (Exception e) {
            // 删除临时上传的文件，删除失败的记录错误，但不影响正常流程
            this.logger.error(e.getMessage());
        }

        String extension = FileTypeUtil.getFileExtension(fileName);//扩展名 不带.号

        String contentType = FileTypeUtil.getContentTypeByExtension(extension);
        if (StringUtils.isBlank(contentType)) {
            throw businessErrors.fileExtensionError(extension);
        }
        AliyunOSSBean aliyunOSSBean = aliyunOSSClient.getObjectFromOSSByKey(tempKey, extension);
        if (aliyunOSSBean == null) {
            throw businessErrors.fileFetchFailure();
        }
        String destinationKey = aliyunOSSBean.toString();
        aliyunOSSClient.copyObject(tempKey, destinationKey, contentType);
        try {
            aliyunOSSClient.deleteObject(tempKey);
        } catch (Exception e) {
            // 删除临时上传的文件，删除失败的记录错误，但不影响正常流程
            this.logger.error(e.getMessage());
        }

        FileBean fileBean = new FileBean();
        fileBean.setFileName(fileName);
        fileBean.setKey(destinationKey);
        fileBean.setLength(aliyunOSSBean.getLength());
        Byte state = 1;
        fileBean.setState(state);
        fileBean.setPreview(state);
        fileBean.setExtension(extension);
        return fileBean;
    }

    /**
     * check
     *
     * @param businessErrors
     * @param fileName
     * @param key
     */
    private void check(BusinessErrors businessErrors, String fileName, String key) {
        if (StringUtils.isBlank(key)) {
            throw businessErrors.fileKeyBlank();
        }

        if (StringUtils.isBlank(fileName)) {
            throw businessErrors.fileNameBlank();
        }

    }

    /**
     * 获取预览接口
     *
     * @param docId
     * @param pageNumber
     * @return resultBean
     */
    @ApiOperation(value = "获取预览")
    @RequestMapping(path = "/preview/{docId}/{pageNumber}", method = {RequestMethod.GET})
    public Object preview(@PathVariable("docId") Long docId, @PathVariable("pageNumber") Integer pageNumber) {
        CommonResultBean resultBean = new CommonResultBean();
        Document document = documentService.fetchById(docId);
        if (document == null) {
            throw BusinessErrors.getInstance().fileNotFound();
        }
        File file = fileService.fetchById(document.getFileId());
        if (file == null) {
            throw BusinessErrors.getInstance().fileNotFound();
        }
        String key = file.getKey();
        key += "-preview-" + pageNumber;
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        int minutes = 60;
        String fileName = key;
        resultBean.setBody(AliyunOSSClientHelper.getInstance().generateGetPresignedUrl(key, fileName, minutes));
        return resultBean;
    }

}

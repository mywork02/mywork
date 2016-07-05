package com.suime.library.rest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.DocShareRecord;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.library.service.DocShareRecordService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.service.StudentService;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.sui.user.remote.service.StudentPointRemoteService;

/**
 * 分享回调
 * @author ice
 */
@Api(description = "发送文档接口", tags = "share")
@RestController
@RequestMapping("/share")
public class ShareRestController extends AbstractRestController {

	/**
	 * studentPointRemoteService
	 */
	@Autowired
	private StudentPointRemoteService studentPointRemoteService;

	/**
	 * studentDocumentService
	 */
	@Autowired
	private StudentDocumentService studentDocumentService;

	/**
	 * studentService
	 */
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private DocShareRecordService docShareRecordService;

	/**
	 * 分享后回调,暂时为移动端调用
	 * @return resultBean
	 */
	@ApiOperation(value = "分享后回调,暂时为移动端调用")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/callback/{docId}", method = { RequestMethod.POST })
	public Object callback(@PathVariable("docId") Long docId) {
		Long userId = BaseUserHelper.getInstance().getUserId();
		if (userId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		StudentDocument fetchById = studentDocumentService.fetchById(docId);
		if (fetchById == null) {
			throw BusinessErrors.getInstance().fileNotFound();
		}
		
		
		Student student = studentService.fetchById(userId);
		

		/**
		 *分享记录
		 */
		Byte actived  = 1;
		DocShareRecord docShareRecord = new DocShareRecord();
		docShareRecord.setStudentId(student.getId());
		docShareRecord.setStudentDocumentId(docId);
		docShareRecord.setStudentNickName(student.getNickName());
		docShareRecord.setActived(actived);
		docShareRecordService.save(docShareRecord);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}
}

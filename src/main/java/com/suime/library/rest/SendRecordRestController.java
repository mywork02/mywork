package com.suime.library.rest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.web.AbstractRestController;
import com.suime.library.service.SendRecordService;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * sendRecordRestController
 * @author ice
 */
@Api(description = "发送文档接口", tags = "senddoc")
@RestController
@RequestMapping("/stdoc/send")
public class SendRecordRestController extends AbstractRestController {

	/**
	 * sendRecordService
	 */
	@Autowired
	private SendRecordService sendRecordService;

	// @ApiOperation(value = "发送文档")
	// @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	// @RequiresAuthentication
	// @RequestMapping(path = "/document", method = RequestMethod.POST)
	// public Object addDocument(@RequestBody SendRecordBean sendRecordBean) {
	// CommonResultBean resultBean = new CommonResultBean();
	// resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
	// Long senderId = BaseUserHelper.getInstance().getUserId();
	// sendRecordBean.setSenderId(senderId);
	// this.sendRecordService.addDocument(sendRecordBean);
	// return resultBean;
	// }
	//
	// @ApiOperation(value = "发送文件夹")
	// @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	// @RequiresAuthentication
	// @RequestMapping(path = "/directory", method = RequestMethod.POST)
	// public Object addDirectory(@RequestBody SendRecordBean sendRecordBean) {
	// CommonResultBean resultBean = new CommonResultBean();
	// resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
	// Long senderId = BaseUserHelper.getInstance().getUserId();
	// sendRecordBean.setSenderId(senderId);
	// this.sendRecordService.addDirectory(sendRecordBean);
	// return resultBean;
	// }
	//
	// @ApiOperation(value = "接收")
	// @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	// @RequiresAuthentication
	// @RequestMapping(path = "/accept/{id}", method = RequestMethod.POST)
	// public Object createAccept(@PathVariable("id") Long id) {
	// CommonResultBean resultBean = new CommonResultBean();
	// resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
	// Long receiverId = BaseUserHelper.getInstance().getUserId();
	// this.sendRecordService.createReceive(id, receiverId);
	// return resultBean;
	// }
	//
	// @ApiOperation(value = "拒收")
	// @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	// @RequiresAuthentication
	// @RequestMapping(path = "/refuse/{id}", method = RequestMethod.POST)
	// public Object createRefuse(@PathVariable("id") Long id) {
	// CommonResultBean resultBean = new CommonResultBean();
	// resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
	// Long receiverId = BaseUserHelper.getInstance().getUserId();
	// this.sendRecordService.createRefuse(id, receiverId);
	// return resultBean;
	// }

	/**
	 * 未处理的发送记录
	 * @param page
	 * @param pageSize
	 * @return resultBean
	 */
	@ApiOperation(value = "未处理的发送记录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/unprocessed", method = RequestMethod.GET)
	public Object fetchUnprocessed(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long receiverId = BaseUserHelper.getInstance().getUserId();
		Page data = this.sendRecordService.fetchPageDto(receiverId, null, page, pageSize);
		resultBean.setBody(data);
		return resultBean;
	}
}

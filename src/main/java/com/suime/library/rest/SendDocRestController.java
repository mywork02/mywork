package com.suime.library.rest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caucho.hessian.client.HessianProxyFactory;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.web.AbstractRestController;
import com.suime.common.error.BusinessErrors;
import com.suime.library.dto.pack.AcceptRecordBean;
import com.suime.library.dto.pack.SendRecordV2Bean;
import com.suime.library.error.SendDocErrors;
import com.suime.library.service.TestTxService;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.sui.api.constants.ResStatusEnum;
import me.sui.api.dto.GetSendDocMsgReqDto;
import me.sui.api.dto.GetSendDocMsgResDto;
import me.sui.api.dto.PushSendDocMsgReqDto;
import me.sui.api.dto.PushSendDocMsgResDto;
import me.sui.api.dto.ReceiveSendDocReqDto;
import me.sui.api.dto.ReceiveSendDocResDto;
import me.sui.api.service.IDocService;

/**
 * SendDocRestController
 *
 * @author david
 */
@Api(description = "发送文档接口", tags = "senddoc")
@RestController
@RequestMapping("/senddoc")
public class SendDocRestController extends AbstractRestController {

	/**
	 * hssUrl
	 */
	@Value("#{configProperties['hss.access.url']}")
	private String hssUrl;

	/**
	 * testTxService
	 */
	@Autowired
	@Qualifier("testTxService")
	private TestTxService testTxService;

	/**
	 * 测试事务
	 * @return resultBean
	 */
	@ApiOperation(value = "测试事务")
	@RequestMapping(path = "/testTx", method = RequestMethod.GET)
	public Object pushSendDocMsg() {

		CommonResultBean resultBean = new CommonResultBean();
		try {
			testTxService.saveTest();
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
			resultBean.setBody(e.getMessage());
			return resultBean;
		}
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * 发送文档或文件夹
	 * @param sendRecordV2Bean
	 * @return resultBean
	 */
	@ApiOperation(value = "触发发送文档事件")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/pushSendDocMsg", method = { RequestMethod.POST })
	public Object pushSendDocMsg(@RequestBody SendRecordV2Bean sendRecordV2Bean) {
		checkSendParams(sendRecordV2Bean);
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}

		PushSendDocMsgReqDto req = new PushSendDocMsgReqDto();
		// req.setDesc(sendRecordV2Bean.getName());// 文件名或者目录名称
		req.setDocIds(sendRecordV2Bean.getDocIds());// 文件ID
		req.setDirIds(sendRecordV2Bean.getDirIds());// 目录ID
		req.setReceiverStudentIds(sendRecordV2Bean.getReceiverStudentIds());// 发送者的学生ID
		req.setSenderStudentId(studentId);// 接受者的学生ID
		req.setRequestTime(DateUtil.getTime().toString());
		req.setSourceCode("wenku");

		try {
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocService docService = (IDocService) hessianProxyFactory.create(IDocService.class, hssUrl + "/api/docService.htm");
			PushSendDocMsgResDto resDto = docService.pushDoc(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw SendDocErrors.getInstance().sendError(e.getMessage());
		}
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * 发送文档时，参数校验
	 * @param sendRecordV2Bean
	 */
	private void checkSendParams(SendRecordV2Bean sendRecordV2Bean) {
		if (sendRecordV2Bean == null || sendRecordV2Bean.getReceiverStudentIds() == null || sendRecordV2Bean.getReceiverStudentIds().isEmpty()) {
			throw BusinessErrors.getInstance().paramsError();
		}
		// if (StringUtils.isBlank(sendRecordV2Bean.getName())) {
		// throw BusinessErrors.getInstance().paramsError();
		// }
		boolean dirIdsBlankFlag = sendRecordV2Bean.getDirIds() == null || sendRecordV2Bean.getDirIds().isEmpty();// 目录id是否为空
		boolean docIdsBlankFlag = sendRecordV2Bean.getDocIds() == null || sendRecordV2Bean.getDocIds().isEmpty();// 文档id是否为空
		if (docIdsBlankFlag && dirIdsBlankFlag) {
			throw BusinessErrors.getInstance().paramsError();
		}
	}

	/**
	 * 查询文档接收事件
	 * @param pageNum
	 * @param pageSize
	 * @param isAccepted
	 * @return resultBean
	 */
	@ApiOperation(value = "反查发送文档事件")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/pullSendDocMsg", method = { RequestMethod.GET })
	public Object pullSendDocMsg(@RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum,
			@RequestParam(name = "pageSize", required = true, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "isAccepted", required = true, defaultValue = "0") Integer isAccepted) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		GetSendDocMsgReqDto req = new GetSendDocMsgReqDto();
		req.setReceiverStudentId(studentId);
		req.setPageNum(pageNum);
		req.setPageSize(pageSize);
		req.setRequestTime(DateUtil.getTime().toString());
		req.setIsAccepted(isAccepted);
		req.setSourceCode("wenku");
		GetSendDocMsgResDto resDto = null;
		try {
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocService docService = (IDocService) hessianProxyFactory.create(IDocService.class, hssUrl + "/api/docService.htm");
			resDto = docService.getMsg(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw SendDocErrors.getInstance().sendError(e.getMessage());
		}

		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(resDto.getResult());
		return resultBean;
	}

	/**
	 * 接收文档发送请求记录
	 * @param acceptRecordBean
	 * @return resultBean
	 */
	@ApiOperation(value = "拒收发送文档事件")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/refuse", method = { RequestMethod.POST })
	public Object refuseSendDocMsg(@RequestBody AcceptRecordBean acceptRecordBean) {
		CommonResultBean resultBean = acceptSendReccord(acceptRecordBean, false);// 接收文档
		return resultBean;
	}

	/**
	 * 接收文档发送请求记录
	 * @param acceptRecordBean
	 * @return resultBean
	 */
	@ApiOperation(value = "接受发送文档事件")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/accept", method = { RequestMethod.POST })
	public Object acceptSendDocMsg(@RequestBody AcceptRecordBean acceptRecordBean) {
		CommonResultBean resultBean = acceptSendReccord(acceptRecordBean, true);// 接收文档
		return resultBean;
	}

	/**
	 * 接收 或 拒收 文档发送请求
	 * @param acceptRecordBean
	 * @param isAccepted 
	 * @return resultBean
	 */
	private CommonResultBean acceptSendReccord(AcceptRecordBean acceptRecordBean, boolean isAccepted) {
		if (acceptRecordBean == null || (acceptRecordBean.getRecordIds() == null || !acceptRecordBean.getRecordIds().isEmpty())) {
			throw BusinessErrors.getInstance().paramsError();
		}
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}

		ReceiveSendDocReqDto req = new ReceiveSendDocReqDto();
		req.setRecordIds(acceptRecordBean.getRecordIds());// 记录ID集
		req.setStudentId(studentId);// 接受学生ID
		req.setRequestTime(DateUtil.getTime().toString());
		req.setSourceCode("wenku");
		req.setAccepted(isAccepted);// true:接受 false:拒绝
		try {
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocService docService = (IDocService) hessianProxyFactory.create(IDocService.class, hssUrl + "/api/docService.htm");
			ReceiveSendDocResDto resDto = docService.receiveDoc(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw SendDocErrors.getInstance().sendError(e.getMessage());
		}

		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

}

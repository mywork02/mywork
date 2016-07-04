package com.suime.library.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import com.suime.library.dto.pack.AddDocToSetBean;
import com.suime.library.dto.pack.DocSetBean;
import com.suime.library.error.DocSetErrors;
import com.suime.library.helper.PageHelper;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.sui.api.constants.ResStatusEnum;
import me.sui.api.dto.base.BaseResDto;
import me.sui.api.dto.doc.AddDocToSetReqDto;
import me.sui.api.dto.doc.CreateDocSetReqDto;
import me.sui.api.dto.doc.CreateDocSetResDto;
import me.sui.api.dto.doc.DeleteDocSetReqDto;
import me.sui.api.dto.doc.DocToSetData;
import me.sui.api.dto.doc.GetDocSetReqDto;
import me.sui.api.dto.doc.GetDocSetResDto;
import me.sui.api.dto.doc.OpenDocSetReqDto;
import me.sui.api.dto.doc.OpenDocSetResDto;
import me.sui.api.dto.doc.RemoveDocFromSetReqDto;
import me.sui.api.dto.doc.UpdateDocSetReqDto;
import me.sui.api.service.IDocSetService;

/**
 * DocSetRestController
 *
 * @author david
 */
@Api(description = "文集接口", tags = "docset")
@RestController
@RequestMapping("/docset")
public class DocSetRestController extends AbstractRestController {

	/**
	 * hssUrl
	 */
	@Value("#{configProperties['hss.access.url']}")
	private String hssUrl;

	/**
	 * 创建文集
	 * @param docSetBean
	 * @return
	 */
	@ApiOperation(value="创建文集") 
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/createDocSet", method = { RequestMethod.POST })
	public Object createDocSet(@RequestBody DocSetBean docSetBean) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		try {
			CreateDocSetReqDto req = new CreateDocSetReqDto();
			req.setRequestTime(DateUtil.getTime().toString());
			req.setSetCover(docSetBean.getSetCover());
			req.setSetDesc(docSetBean.getSetDesc());
			req.setSetName(docSetBean.getSetName());
			req.setSourceCode("wenku");
			req.setStudentId(studentId);
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocSetService docSetService = (IDocSetService) hessianProxyFactory.create(IDocSetService.class, hssUrl + "/api/docSetService.htm");
			CreateDocSetResDto resDto = docSetService.createDocSet(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw DocSetErrors.getInstance().docSetErrors(e.getMessage());
		}
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}
	
	/**
	 * 删除文集
	 * @param setId
	 * @return
	 */
	@ApiOperation(value = "删除文集")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/deleteDocSet", method = { RequestMethod.DELETE })
	public Object deleteDocSet(@RequestParam(name = "setId", required = true) Long setId) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}

		try {
			DeleteDocSetReqDto req = new DeleteDocSetReqDto();
			req.setRequestTime(DateUtil.getTime().toString());
			req.setSourceCode("wenku");
			req.setStudentId(studentId);
			req.setSetId(setId);
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocSetService docSetService = (IDocSetService) hessianProxyFactory.create(IDocSetService.class, hssUrl + "/api/docSetService.htm");
			BaseResDto resDto = docSetService.deleteDocSet(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw DocSetErrors.getInstance().docSetErrors(e.getMessage());
		}

		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * 更新文集
	 * @param docSetBean
	 * @return
	 */
	@ApiOperation(value = "更新文集")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/updateDocSet", method = { RequestMethod.POST })
	public Object updateDocSet(@RequestBody DocSetBean docSetBean) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}

		try {
			UpdateDocSetReqDto req = new UpdateDocSetReqDto();
			req.setRequestTime(DateUtil.getTime().toString());
			req.setSetCover(docSetBean.getSetCover());
			req.setSetDesc(docSetBean.getSetDesc());
			req.setSetName(docSetBean.getSetName());
			req.setSourceCode("wenku");
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocSetService docSetService = (IDocSetService) hessianProxyFactory.create(IDocSetService.class, hssUrl + "/api/docSetService.htm");
			BaseResDto resDto = docSetService.updateDocSet(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw DocSetErrors.getInstance().docSetErrors(e.getMessage());
		}
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * 查询文集
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ApiOperation(value = "查询文集")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/getDocSet", method = { RequestMethod.GET })
	public Object getDocSet(@RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", required = true,
			defaultValue = "10") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}

		try {
			GetDocSetReqDto req = new GetDocSetReqDto();
			req.setRequestTime(DateUtil.getTime().toString());
			req.setSourceCode("wenku");
			req.setPageNum(pageNum);
			req.setPageSize(pageSize);
			req.setStudentId(studentId);
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocSetService docSetService = (IDocSetService) hessianProxyFactory.create(IDocSetService.class, hssUrl + "/api/docSetService.htm");
			GetDocSetResDto resDto = docSetService.getDocSet(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
			resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
			resultBean.setBody(resDto.getResult());
			return PageHelper.generatePage(pageNum, pageSize, Long.valueOf(resDto.getResult().getPageInfo().getTotal()).intValue(), resDto.getResult().getGetDocSetDataDto());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw DocSetErrors.getInstance().docSetErrors(e.getMessage());
		}
	}

	/**
	 * 把文件或者文件夹添加到文集
	 * @param addDocToSetBeans
	 * @return
	 */
	@ApiOperation(value = "把文件或者文件夹添加到文集")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/addDocToSet", method = { RequestMethod.POST })
	public Object addDocToSet(@RequestBody List<AddDocToSetBean> addDocToSetBeans) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}

		try {
			AddDocToSetReqDto req = new AddDocToSetReqDto();
			List<DocToSetData> docToSetDatas = new ArrayList<DocToSetData>();
			for (int i = 0; addDocToSetBeans != null && !addDocToSetBeans.isEmpty() && i < addDocToSetBeans.size(); i++) {
				AddDocToSetBean addDocToSetBean = addDocToSetBeans.get(i);
				DocToSetData docToSetData = new DocToSetData();
				docToSetData.setDocId(addDocToSetBean.getDocId());
				docToSetData.setDocType(addDocToSetBean.getDocType());
				docToSetData.setStudentId(studentId);
				docToSetData.setSetId(addDocToSetBean.getSetId());
				docToSetDatas.add(docToSetData);
			}
			req.setDocToSetDatas(docToSetDatas);
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocSetService docSetService = (IDocSetService) hessianProxyFactory.create(IDocSetService.class, hssUrl + "/api/docSetService.htm");
			BaseResDto resDto = docSetService.addDocToSet(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw DocSetErrors.getInstance().docSetErrors(e.getMessage());
		}
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}
		
	
	/**
	 * 从文集中移除文件或者文件夹
	 * @param refId
	 * @return
	 */
	@ApiOperation(value = "从文集中移除文件或者文件夹")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/removeDocFromSet", method = { RequestMethod.DELETE })
	public Object removeDocFromSet(@RequestParam(name = "refId", required = true) Long refId) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		try {
			RemoveDocFromSetReqDto req = new RemoveDocFromSetReqDto();
			req.setRefId(refId);
			req.setStudentId(studentId);
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocSetService docSetService = (IDocSetService) hessianProxyFactory.create(IDocSetService.class, hssUrl + "/api/docSetService.htm");
			BaseResDto resDto = docSetService.removeDocFromSet(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw DocSetErrors.getInstance().docSetErrors(e.getMessage());
		}
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * 打开文集
	 * @param pageNum
	 * @param pageSize
	 * @param setId
	 * @return
	 */
	@ApiOperation(value = "打开文集")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/openDocSet", method = { RequestMethod.GET })
	public Object openDocSet(@RequestParam(name = "page", required = true, defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", required = true,
			defaultValue = "10") Integer pageSize, @RequestParam(name = "setId", required = true) Long setId) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}

		try {
			OpenDocSetReqDto req = new OpenDocSetReqDto();
			req.setRequestTime(DateUtil.getTime().toString());
			req.setSourceCode("wenku");
			req.setPageNum(pageNum);
			req.setPageSize(pageSize);
			req.setStudentId(studentId);
			req.setSetId(setId);
			HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
			hessianProxyFactory.setOverloadEnabled(true);
			IDocSetService docSetService = (IDocSetService) hessianProxyFactory.create(IDocSetService.class, hssUrl + "/api/docSetService.htm");
			OpenDocSetResDto resDto = docSetService.openDocSet(req);// 发送事件
			if (!ResStatusEnum.SUCCESS.equals(resDto.getResStatusEnum())) {
				resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
				resultBean.setBody(resDto.getErrorInfo());
				return resultBean;
			}
			resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
			resultBean.setBody(resDto.getResult());
			return PageHelper.generatePage(pageNum, pageSize, Long.valueOf(resDto.getResult().getPageInfo().getTotal()).intValue(), resDto.getResult().getOpenDocSetDataDto());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw DocSetErrors.getInstance().docSetErrors(e.getMessage());
		}
	}

}

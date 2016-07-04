package com.suime.library.rest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.SuimeLibraryConstants;
import com.suime.library.dto.MarkRecordDto;
import com.suime.library.dto.PointResultDto;
import com.suime.library.dto.pack.MarkRecordBean;
import com.suime.library.service.MarkRecordService;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * markRecordRestController
 *
 * @author ice
 */
@Api(description = "收藏接口", tags = "mark")
@RestController
@RequestMapping("/mark")
public class MarkRecordRestController extends AbstractRestController {

	/**
	 * markRecordService
	 */
	@Autowired
	private MarkRecordService markRecordService;

	/**
	 * add markRecord
	 * @param markRecordBean
	 * @return resultBean
	 */
	@ApiOperation(value = "添加收藏记录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/add", method = { RequestMethod.POST })
	public Object add(@RequestBody MarkRecordBean markRecordBean) {
		if (markRecordBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		markRecordBean.setStudentId(studentId);
		markRecordBean.setAssociatedId(markRecordBean.getStudentDocumentId());
		markRecordBean.setType(SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT);
		MarkRecordDto markRecordDto = markRecordService.addMarkRecord(markRecordBean);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(markRecordDto);
		return resultBean;
	}

	/**
	 * add markRecord
	 * @param markRecordBean
	 * @return resultBean
	 */
	@ApiOperation(value = "添加文集收藏记录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/addWorks", method = { RequestMethod.POST })
	public Object addWorks(@RequestBody MarkRecordBean markRecordBean) {
		if (markRecordBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		markRecordBean.setStudentId(studentId);
		markRecordBean.setType(SuimeLibraryConstants.MARK_RECORD_TYPE_COLLECTED_WORKS);
		MarkRecordDto markRecordDto = markRecordService.addMarkRecord(markRecordBean);
		PointResultDto pointResultDto = new PointResultDto();
		pointResultDto.setPoint(markRecordDto.getPoint());
		markRecordDto.setPoint(null);
		pointResultDto.setMarkRecord(markRecordDto);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(pointResultDto);
		return resultBean;
	}

	/**
	 * remove markRecord
	 * @param docId
	 * @return resultBean
	 */
	@Deprecated
	@ApiOperation(value = "删除收藏记录---此接口设计的不够合理,后续版本中请不要使用")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/remove/{docId}", method = { RequestMethod.DELETE })
	public Object remove(@PathVariable("docId") Long docId) {
		MarkRecordBean markRecordBean = new MarkRecordBean();
		markRecordBean.setStudentDocumentId(docId);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		markRecordBean.setStudentId(studentId);
		markRecordBean.setAssociatedId(docId);
		markRecordBean.setType(SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT);
		markRecordService.removeDocByItem(markRecordBean);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * 根据收藏记录id，删除收藏记录
	 * @param markRecordId
	 * @return resultBean
	 */
	@ApiOperation(value = "根据收藏记录id，删除收藏记录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/removeById/{markRecordId}", method = { RequestMethod.DELETE })
	public Object removeById(@PathVariable("markRecordId") Long markRecordId) {
		MarkRecordBean markRecordBean = new MarkRecordBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		markRecordBean.setId(markRecordId);
		markRecordBean.setStudentId(studentId);
		markRecordService.removeDocByItem(markRecordBean);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * update markRecord
	 * @param markRecordBean
	 * @return resultBean
	 */
	@ApiOperation(value = "修改收藏记录(移动到某一收藏夹),修改收藏记录别名")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/update", method = { RequestMethod.POST })
	public Object update(@RequestBody MarkRecordBean markRecordBean) {
		if (markRecordBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		markRecordBean.setStudentId(studentId);
		markRecordBean.setAssociatedId(markRecordBean.getStudentDocumentId());
		markRecordBean.setType(SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT);
		MarkRecordDto markRecordDto = markRecordService.updateMarkRecord(markRecordBean);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(markRecordDto);
		return resultBean;
	}

}

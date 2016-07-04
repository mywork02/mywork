package com.suime.library.rest.v2;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/v2/mark")
public class MarkRecordV2RestController extends AbstractRestController {

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
	@ApiOperation(value = "添加收藏记录,v2 版本,返回相应的获取积分的信息")
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
		PointResultDto pointResultDto = new PointResultDto();
		pointResultDto.setPoint(markRecordDto.getPoint());
		markRecordDto.setPoint(null);

		pointResultDto.setMarkRecord(markRecordDto);

		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(pointResultDto);
		return resultBean;
	}

}

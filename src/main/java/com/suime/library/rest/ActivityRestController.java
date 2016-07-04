package com.suime.library.rest;

import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Activity;
import com.suime.library.dto.ActivityDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.SearchBean;
import com.suime.library.error.ActivityErrors;
import com.suime.library.service.ActivityService;
import com.suime.library.service.StudentDocumentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import java.util.ArrayList;
import java.util.List;

/**
 * activityRestController
 *
 * @author ice
 */
@Api(description = "活动接口", tags = "activity")
@RestController
@RequestMapping("/activity")
public class ActivityRestController extends AbstractRestController {

	@Autowired
	private StudentDocumentService studentDocumentService;

	@Autowired
	private ActivityService activityService;

	/**
	 * 根据条件分页获取
	 *
	 * @param code
	 * @param sort
	 * @param sortField
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(path = "/page", method = RequestMethod.GET)
	public Object list(@RequestParam(name = "code", required = false) String code, @RequestParam(name = "sort", defaultValue = "DESC") String sort,
			@RequestParam(name = "sortField", defaultValue = "orderNum") String sortField, @RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		SearchBean adminBean = new SearchBean();
		adminBean.setText(code);
		adminBean.setSortField(sortField);
		adminBean.setSort(OrderType.valueOf(sort));
		Conds conds = new Conds();
		if (StringUtils.isNotBlank(code)) {
			conds.equal("code", code);
		}
		conds.equal("actived", 1);
		Sort tempSort;
		if (StringUtils.equals("createdAt", sortField)) {
			tempSort = new Sort("created_at", OrderType.valueOf(sort));
		} else {
			tempSort = new Sort("order_num", OrderType.valueOf(sort));
		}
		Page result = this.activityService.fetchDtoPageSearchByPage(conds, tempSort, page, pageSize);
		resultBean.setBody(result);
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * 根据code获取活动详情
	 *
	 * @param code
	 * @return
	 */
	@ApiOperation(value = "根据code获取活动详情", response = CommonResultBean.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "success", response = Activity.class) })
	@RequestMapping(path = "/info/{code}", method = RequestMethod.GET)
	public Object info(@PathVariable("code") String code, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		if (StringUtils.isBlank(code)) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Conds conds = new Conds();
		conds.equal("code", code);
		Activity activity = this.activityService.fetchSearchByConds(conds);
		if (activity == null) {
			throw ActivityErrors.getInstance().activityNotFoundError(code);
		}
		ActivityDto activityDto = new ActivityDto(activity);
		String keyWords = activityDto.getKeyWords();
		String subTitles = activityDto.getSubTitles();
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		String[] wordArray = keyWords.split("[,，]");
		String[] subTitleArray = subTitles.split("[,，]");
		if (wordArray.length != subTitleArray.length) {
			throw ActivityErrors.getInstance().keyWordsNumberNotEqualSubTitlesNumberError(keyWords, subTitles);
		}
		List<JSONObject> list = new ArrayList<>();
		SearchBean searchBean = new SearchBean();
		String sortField = "createdAt";
		searchBean.setSortField(sortField);
		searchBean.setSort(OrderType.DESC);
		// 查询时tag不加分隔符
		searchBean.setIsSplit(false);
		// Integer pageSize = 20;
		for (int n = 0, length = wordArray.length; n < length; n++) {
			if (StringUtils.isNotBlank(wordArray[n])) {
				searchBean.setKeyWord(wordArray[n]);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("tag", wordArray[n]);
				jsonObject.put("subTitle", subTitleArray[n]);
				List<StudentDocumentDto> limitListByItem = this.studentDocumentService.limitListByItem(searchBean, pageSize);
				jsonObject.put("list", limitListByItem);
				if (limitListByItem != null && !limitListByItem.isEmpty()) {
					list.add(jsonObject);
				}
			}
		}
		activityDto.setDatas(list);
		resultBean.setBody(activityDto);
		return resultBean;
	}

}

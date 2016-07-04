package com.suime.library.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.aliyun.mns.common.http.ServiceClient.Request;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.JsonUtil;
import com.confucian.mybatis.support.scope.OrderType;
import com.suime.common.cache.CacheService;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.School;
import com.suime.context.model.Tags;
import com.suime.library.dto.SchoolNumberDto;
import com.suime.library.dto.TagsDto;
import com.suime.library.dto.pack.SearchBean;
import com.suime.library.service.SchoolNumberService;
import com.suime.library.service.SchoolService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.service.TagsService;
import com.suime.library.service.support.OrderFieldEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@Api(description = "专题接口", tags = "dissertation")
@RestController
@RequestMapping("/dissertation")
public class DissertationRestController {

	@Autowired
	private SchoolNumberService schoolNumberService;

	@Autowired
	private StudentDocumentService studentDocumentService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private TagsService tagsService;
	
	@Autowired
	private CacheService cacheService;
	
	/**
	 * stdoc/index 接口缓存key
	 */
	private final String KEY_Dissertation_BY_SCHOOL = "key_dissertation_by_school_";

	@ApiOperation(value = "获取前十热门学校")
	@RequestMapping(path = "/hotschool", method = { RequestMethod.GET })
	public Object listschool(
			@RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
		List<SchoolNumberDto> hotList = schoolNumberService.findhotList(Integer
				.valueOf(pageSize));
		CommonResultBean resultBean = new CommonResultBean();
		Map<String,Object> rtnObj = new HashMap<String,Object>();
		rtnObj.put("data", hotList);
		resultBean.setBody(rtnObj);
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}
	
	@ApiOperation(value="获取专题标签列表")
	@RequestMapping(path="/tags",method = {RequestMethod.GET})
	public Object listtags(){
		List<TagsDto> tagsList = tagsService.findDissertation();
		CommonResultBean resultBean = new CommonResultBean();
		Map<String,Object> rtnObj = new HashMap<String,Object>();
		rtnObj.put("data", tagsList);
		resultBean.setBody(rtnObj);
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	@ApiOperation(value = "专题首页")
	@RequestMapping(path = "/dissindex", method = { RequestMethod.GET })
	public Object dissindex(
			@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,//
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) Integer pageSize,// 热门学校数量
			@RequestParam(name = "sortField", defaultValue = "readCount", required = false) String sortField,// 文档排序字段
			@RequestParam(name = "sort", defaultValue = "DESC") String sort) {// 排序方式
		Page schoolpage = schoolNumberService.findhotdocList(page, pageSize);// 获取有专业资料的的热门学校
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		SearchBean searchBean = new SearchBean();
		if (StringUtils.isNotBlank(sortField)){
			if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.MARK_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.MARK_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.PRINT_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.PRINT_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.READ_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.READ_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.RATINGS.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.RATINGS.getField());
			} else {
				searchBean.setSortField("createdAt");
			}
		}
		searchBean.setSort(OrderType.valueOf(sort.toUpperCase()));
		searchBean.setSortField(sortField);
		List<Map<String, Object>>  datalist =new ArrayList<Map<String,Object>>();
		Map<String, Object> data=null;
		Object dissertationBySchool = cacheService.get(KEY_Dissertation_BY_SCHOOL);
		 Map<String, Object> result = new HashMap<String, Object>();
		final int exp = 60 * 60 * 24; // 一天
		if(dissertationBySchool==null){
			if (schoolpage!=null && schoolpage.getData()!=null && schoolpage.getData().size() > 0) {
				for (Object obj : schoolpage.getData()) {//根据学校进行获取学校下的资料
					data = new HashMap<String, Object>();
					SchoolNumberDto school = (SchoolNumberDto) obj;
					searchBean.setSchoolId(school.getSchoolId());
					Page docpage = studentDocumentService.pageByItemDissertation(searchBean, 1, 6);
					data.put("schoolId", school.getSchoolId());
					data.put("schoolName", school.getSchoolName());
					data.put("schooldoc",docpage.getData());
					datalist.add(data);
				}
				result.put("data",JsonUtil.toJsonArray(datalist));
			 cacheService.set(KEY_Dissertation_BY_SCHOOL, exp, JsonUtil.toJsonString(datalist));
			}
		}else{
			result.put("data",JsonUtil.toJsonArray(dissertationBySchool));
		}
	    result.put("totalPageCount", schoolpage.getTotalPageCount());
	    result.put("currentPageNo", schoolpage.getCurrentPageNo());
		resultBean.setBody(result);
		return resultBean;
	}
	@ApiOperation(value = "根据学校获取资料")
	@RequestMapping(path = "/schooldata", method = { RequestMethod.GET })
	public Object schooldata(
			@RequestParam(name = "page", defaultValue = "0", required = false) Integer page,//
			@RequestParam(name = "pageSize", defaultValue = "4", required = false) Integer pageSize,// 资料数量
			@RequestParam(name = "sortField", defaultValue = "readCount", required = false) String sortField,// 文档排序字段
			@RequestParam(name = "sort", defaultValue = "DESC") String sort,
			 @RequestParam(name = "tag", required = false) Long tagId,
			@RequestParam(name = "schoolId") Long schoolId){// 排序方式
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		SearchBean searchBean = new SearchBean();
		if (StringUtils.isNotBlank(sortField)){
			if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.MARK_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.MARK_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.PRINT_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.PRINT_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.READ_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.READ_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(
					OrderFieldEnum.RATINGS.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.RATINGS.getField());
			} else {
				searchBean.setSortField("createdAt");
			}
		}
		searchBean.setSort(OrderType.valueOf(sort.toUpperCase()));
		searchBean.setSortField(sortField);
		searchBean.setSchoolId(schoolId);
		searchBean.setTagId(tagId);
		Page pages = studentDocumentService.pageByItemDissertation(searchBean, page, pageSize);//获取文章
		School school = schoolService.fetchById(schoolId);
		if (school == null) {
	            throw BusinessErrors.getInstance().paramsError();
	    }
		Map<String,Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("schoolName",school.getName());
		rtnMap.put("schoolId",school.getId());
		rtnMap.put("data",pages);
		resultBean.setBody(rtnMap);
		return resultBean;
	}
	
	/*
	@ApiOperation(value = "根据标签获取资料")
	@RequestMapping(path = "/tagsdata", method = { RequestMethod.GET })
	public Object schooldata(
			@RequestParam(name = "pageSize",defaultValue = "4", required = false) Integer pageSize,// 热门学校数量
			@RequestParam(name = "sortField",defaultValue = "readCount", required = false) String sortField,// 文档排序字段
			@RequestParam(name = "sort", defaultValue = "DESC") String sort,
			@RequestParam(name = "schoolId") Long schoolId){// 排序方式
	}
	}*/
}

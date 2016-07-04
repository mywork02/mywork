package com.suime.library.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.JsonUtil;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.cache.CacheService;
import com.suime.common.error.BusinessErrors;
import com.suime.common.helper.AliyunOSSClientHelper;
import com.suime.common.util.FileTypeUtil;
import com.suime.constants.PermissionType;
import com.suime.constants.SourceType;
import com.suime.constants.SuimeLibraryConstants;
import com.suime.context.model.File;
import com.suime.context.model.PrintfilePrintshopRef;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.library.dto.CategoryDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.CategoryBean;
import com.suime.library.dto.pack.MarkRecordBean;
import com.suime.library.dto.pack.SearchBean;
import com.suime.library.dto.pack.StudentDocumentBean;
import com.suime.library.service.CategoryService;
import com.suime.library.service.DirectoryContentRelsService;
import com.suime.library.service.DirectoryService;
import com.suime.library.service.DocRecommendService;
import com.suime.library.service.FileService;
import com.suime.library.service.MarkRecordService;
import com.suime.library.service.OperationRecordService;
import com.suime.library.service.PrintfilePrintshopRefService;
import com.suime.library.service.SendRecordService;
import com.suime.library.service.StdocStickRemovedService;
import com.suime.library.service.StudentDocumentSearchService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.service.StudentService;
import com.suime.library.service.support.OrderFieldEnum;
import com.suime.library.shiro.BaseUserHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.sui.api.dto.search.PushSearchKeyReqDto;
import me.sui.api.service.IDocService;
import me.sui.framework.hessian.HessianServerClientHolder;

/**
 * studentDocumentRestController
 * @author ice
 */
@Api(description = "文档接口", tags = "stdoc")
@RestController
@RequestMapping("/stdoc")
public class StudentDocumentRestController extends AbstractRestController {

	/**
	 * studentDocumentService
	 */
	@Autowired
	private StudentDocumentService studentDocumentService;

	/**
	 * studentDocumentSearchService
	 */
	@Autowired
	private StudentDocumentSearchService studentDocumentSearchService;

	/**
	 * studentService
	 */
	@Autowired
	private StudentService studentService;

	/**
	 * directoryService
	 */
	@Autowired
	private DirectoryService directoryService;

	/**
	 * markRecordService
	 */
	@Autowired
	private MarkRecordService markRecordService;

	/**
	 * categoryService
	 */
	@Autowired
	private CategoryService categoryService;

	/**
	 * sendRecordService
	 */
	@Autowired
	private SendRecordService sendRecordService;

	/**
	 * operationRecordService
	 */
	@Autowired
	private OperationRecordService operationRecordService;

	/**
	 * stdocStickRemovedService
	 */
	@Autowired
	private StdocStickRemovedService stdocStickRemovedService;

	/**
	 * docRecommendService
	 */
	@Autowired
	private DocRecommendService docRecommendService;

	@Autowired
	private DirectoryContentRelsService directoryContentRelsService;

	@Autowired
	private PrintfilePrintshopRefService printfilePrintshopRefService;
	@Autowired
	private FileService fileService;

	/**
	 * cacheService
	 */
	@Autowired
	private CacheService cacheService;

	@Value("#{configProperties['hss.access.url']}")
	private String hssUrl;

	/**
	 * stdoc/index 接口缓存key
	 */
	private final String KEY_INDEX_BY_SCHOOL = "key_index_by_school_";

	/**
	 * stdoc/recommend 接口缓存key
	 */
	private final String KEY_INDEX_BY_RECOMMEND = "key_index_by_recommend";

	/**
	 * 首页 推荐列表
	 *
	 * @return resultBean
	 */
	@ApiOperation(value = "首页推荐列表")
	@RequestMapping(path = "/index", method = { RequestMethod.GET })
	public Object index(@RequestParam(name = "pageSize", defaultValue = "6") Integer pageSize) {
		Long userId = BaseUserHelper.getInstance().getUserId();
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		SearchBean searchBean = new SearchBean();
		searchBean.setSort(OrderType.DESC);
		String sortField = "createdAt";
		searchBean.setSortField(sortField);
		searchBean.setCategoryId(1L);// 专业资料
		searchBean.setStudentId(userId);

		Long schoolId = 0L;
		if (userId != null) {
			Student student = studentService.fetchById(userId);
			if (student != null && student.getSchoolId() != null) {
				schoolId = student.getSchoolId();
			}
		}
		Object indexBySchool = cacheService.get(KEY_INDEX_BY_SCHOOL + schoolId.toString());
		final int exp = 60 * 60 * 24; // 一天
		if (indexBySchool == null) {
			final Integer rowNumbers = 6;
			List<StudentDocumentDto> list1 = studentDocumentService.limitListByItem(searchBean, pageSize);
			// 2通用课程
			searchBean.setCategoryId(2L);// 通用课程
			List<StudentDocumentDto> list2 = studentDocumentService.limitListByItem(searchBean, pageSize);
			// 3实用文档
			searchBean.setCategoryId(3L);// 实用文档
			List<StudentDocumentDto> list3 = studentDocumentService.limitListByItem(searchBean, pageSize);
			// 4资格考试
			searchBean.setCategoryId(4L);// 资格考试
			List<StudentDocumentDto> list4 = studentDocumentService.limitListByItem(searchBean, pageSize);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("category1", list1);
			jsonObject.put("category2", list2);
			jsonObject.put("category3", list3);
			jsonObject.put("category4", list4);

			resultBean.setBody(jsonObject);
			cacheService.set(KEY_INDEX_BY_SCHOOL + schoolId.toString(), exp, JsonUtil.toJsonString(jsonObject));
		} else {
			resultBean.setBody(JsonUtil.toJsonObject(indexBySchool));
		}
		return resultBean;
	}

	/**
	 * list
	 *
	 * @param tagId
	 * @param text
	 * @param sortField
	 * @param sort
	 * @param curPage
	 * @param pageSize
	 * @return resultBean
	 */
	@ApiOperation(value = "根据条件获取文档，条件为空时获取所有文档")
	@RequestMapping(path = "/list", method = { RequestMethod.GET })
	public Object list(@RequestParam(name = "category", required = false) Long categoryId, @RequestParam(name = "tag", required = false) Long tagId,
			@RequestParam(name = "text", required = false) String text, @RequestParam(name = "sortField", required = false) String sortField,
			@RequestParam(name = "sort", defaultValue = "DESC") String sort, @RequestParam(name = "page", defaultValue = "1") Integer curPage,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		SearchBean searchBean = new SearchBean();
		searchBean.setSort(OrderType.valueOf(sort.toUpperCase()));
		searchBean.setTagId(tagId);
		if (StringUtils.isNotBlank(sortField)) {
			if (StringUtils.equalsIgnoreCase(OrderFieldEnum.MARK_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.MARK_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(OrderFieldEnum.PRINT_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.PRINT_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(OrderFieldEnum.READ_COUNT.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.READ_COUNT.getField());
			} else if (StringUtils.equalsIgnoreCase(OrderFieldEnum.RATINGS.getField(), sortField)) {
				searchBean.setSortField(OrderFieldEnum.RATINGS.getField());
			} else {
				searchBean.setSortField("createdAt");
			}
		}
		searchBean.setText(text);
		if (StringUtils.isNotBlank(text)) {
			// 搜索记录留痕
			try {
				PushSearchKeyReqDto req = new PushSearchKeyReqDto();
				req.setSourceCode("wenku");
				req.setCategoryId(categoryId);
				req.setTagId(tagId);
				req.setText(text);
				IDocService docService = HessianServerClientHolder.create(IDocService.class, hssUrl + "/api/docService.htm");
				docService.PushSearchKey(req);// 发送事件
			} catch (Exception e) {
			}
		}
		searchBean.setCategoryId(categoryId);
		Long userId = BaseUserHelper.getInstance().getUserId();
		searchBean.setStudentId(userId);
		Page result = studentDocumentSearchService.search(searchBean, curPage, pageSize);
		resultBean.setBody(result);
		return resultBean;
	}

	/**
	 * info
	 *
	 * @param id
	 * @return resultBean
	 */
	@ApiOperation(value = "获取文档信息")
	@RequestMapping(path = "/info/{id}", method = { RequestMethod.GET })
	public Object info(@PathVariable("id") Long id) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		Long adminUserId = BaseUserHelper.getInstance().getAdminUserIdByCookie();
		Boolean isAdmin = (adminUserId != null);
		StudentDocumentDto dto = this.studentDocumentService.updateAndGetInfo(id, studentId, isAdmin);
		resultBean.setBody(dto);
		return resultBean;
	}

	/**
	 * add
	 *
	 * @param studentDocumentBeans
	 * @return resultBean
	 */
	@ApiOperation(value = "添加文档")
	@RequiresAuthentication
	@RequestMapping(path = "/add", method = { RequestMethod.POST })
	public Object add(@RequestBody List<StudentDocumentBean> studentDocumentBeans, HttpServletRequest request) {
		if (studentDocumentBeans == null || studentDocumentBeans.isEmpty()) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long userId = BaseUserHelper.getInstance().getUserId();
		this.logger.info("------------------userId:" + userId);
		if (userId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		Integer errorNumber = 0;
		Byte source = 1;
		Byte recommended = 0;
		String requestURI = request.getRequestURI();
		boolean isMobile = false;
		if (!StringUtils.contains(requestURI, "/rest/stdoc/add")) {
			isMobile = true;
		}

		JSONArray jsonArray = new JSONArray();
		int index = 0;
		for (StudentDocumentBean studentDocumentBean : studentDocumentBeans) {
			JSONObject tempJson = new JSONObject();
			Long documentId = studentDocumentBean.getDocumentId();
			tempJson.put("documentId", documentId);
			tempJson.put("index", index++);
			try {
				studentDocumentBean.setStudentId(userId);
				if (studentDocumentBean.getSource() == null) {
					studentDocumentBean.setSource(source);
				}
				studentDocumentBean.setRecommended(recommended);
				// if (isMobile) {
				Byte permission = 1;// 私有
				studentDocumentBean.setPermission(permission);
				// }
				studentDocumentBean.setReviewState(Byte.valueOf("1"));// 移动端默认审核不通过
				StudentDocumentDto studentDocumentDto = studentDocumentService.addStudentDocument(studentDocumentBean);
				tempJson.put("stdoc", studentDocumentDto);
				tempJson.put("success", true);
			} catch (Exception ex) {
				ex.printStackTrace();
				errorNumber++;
				this.logger.error(ex.getMessage());
				tempJson.put("success", false);
			}
			jsonArray.add(tempJson);
		}
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		if (errorNumber > 0) {
			resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
		}
		resultBean.setBody(jsonArray);
		return resultBean;
	}

	/**
	 * update
	 *
	 * @param studentDocumentBean
	 * @return resultBean
	 */
	@ApiOperation(value = "更新文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/update", method = { RequestMethod.POST })
	public Object update(@RequestBody StudentDocumentBean studentDocumentBean) {
		if (studentDocumentBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long userId = BaseUserHelper.getInstance().getUserId();
		if (userId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		studentDocumentBean.setStudentId(userId);
		StudentDocumentDto dto = studentDocumentService.updateStudentDocument(studentDocumentBean);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(dto);
		return resultBean;
	}

	/**
	 * 更改目录结构
	 * @param studentDocumentBean
	 * @return resultBean
	 */
	@ApiOperation(value = "更改文档目录")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/changeDir", method = RequestMethod.POST)
	public Object changeDir(@RequestBody StudentDocumentBean studentDocumentBean) {
		if (studentDocumentBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long userId = BaseUserHelper.getInstance().getUserId();
		if (userId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		StudentDocumentBean sdb = new StudentDocumentBean();
		sdb.setStudentId(userId);
		sdb.setDirectoryId(studentDocumentBean.getDirectoryId());
		sdb.setId(studentDocumentBean.getId());
		this.studentDocumentService.updateStudentDocument(sdb);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		return resultBean;
	}

	/**
	 * remove
	 *
	 * @return resultBean
	 */
	@ApiOperation(value = "删除文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/remove", method = { RequestMethod.DELETE })
	public Object remove(@RequestParam("id") Long id) {
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		StudentDocument studentDocument = studentDocumentService.fetchById(id);
		if (!studentId.equals(studentDocument.getStudentId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		if (studentDocument.getStick() != null && studentDocument.getStick().intValue() == 1) {
			stdocStickRemovedService.addRemovedRecord(studentId, studentDocument.getDocumentId());
		} else {
			Byte actived = 0;
			studentDocument.setActived(actived);
			studentDocumentService.updateByRels(studentDocument);
		}
		return resultBean;
	}

	/**
	 * 楼长上传的文档，根据building 查询
	 * @param page     当前页
	 * @param pageSize 每页显示条数
	 * @return resultBean
	 */
	@ApiOperation(value = "楼长上传的文档，根据building 查询")
	@RequestMapping(path = "/listByBuilding/{buildingId}", method = { RequestMethod.GET })
	public Object listByBuilding(@PathVariable("buildingId") Long buildingId, @RequestParam(name = "tagsId", required = false) Long tagsId,
			@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		Student student = studentService.fetchPrintshopStudentByBuilding(buildingId);
		if (student == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		Long studentId = student.getId();
		return fetchShopDocs(studentId, tagsId, page, pageSize);
	}

	/**
	 * 楼长上传的文档，根据 shopId 查询
	 * @param page     当前页
	 * @param pageSize 每页显示条数
	 * @return resultBean
	 */
	@ApiOperation(value = "楼长上传的文档，根据 shopId 查询")
	@RequestMapping(path = "/listByShop/{shopId}", method = { RequestMethod.GET })
	public Object listByShop(@PathVariable("shopId") Long shopId, @RequestParam(name = "tagsId", required = false) Long tagsId,
			@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		Conds conds = new Conds();
		conds.equal("linked_printshop_id", shopId);
		Student student = studentService.fetchSearchByConds(conds);
		if (student == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		Long studentId = student.getId();
		return fetchShopDocs(studentId, tagsId, page, pageSize);
	}

	/**
	 * 用户上传的文档
	 *
	 * @param page     当前页
	 * @param pageSize 每页显示条数
	 * @return resultBean
	 */
	@ApiOperation(value = "用户上传的文档")
	@RequestMapping(path = "/listByUser/{userId}", method = { RequestMethod.GET })
	public Object listByUser(@PathVariable("userId") Long userId, @RequestParam(name = "tagsId", required = false) Long tagsId,
			@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		Student student = studentService.fetchById(userId);
		if (student == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		Long studentId = student.getId();
		return fetchShopDocs(studentId, tagsId, page, pageSize);
	}

	/**
	 * 获取楼长的推荐文档
	 * @param studentId
	 * @param tagsId
	 * @param page
	 * @param pageSize
	 * @return resultBean
	 */
	private Object fetchShopDocs(Long studentId, Long tagsId, Integer page, Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
		Byte actived = 1;
		studentDocumentBean.setActived(actived);
		studentDocumentBean.setStudentId(studentId);
		Byte reviewState = 2;// 通过的文档
		studentDocumentBean.setReviewState(reviewState);
		Byte permission = 1;// 公开
		studentDocumentBean.setPermission(permission);
		studentDocumentBean.setTagsId(tagsId);
		Page pageResult = this.studentDocumentService.pagePersonalDocumentWidthIntro(studentDocumentBean, page, pageSize);
		resultBean.setBody(pageResult);
		return resultBean;
	}

	/**
	 * 我的文档
	 *
	 * @param page     当前页
	 * @param pageSize 每页显示条数
	 * @return resultBean
	 */
	@ApiOperation(value = "我上传的文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/my", method = { RequestMethod.GET })
	public Object my(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {

		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		Byte actived = 1;
		studentDocumentBean.setActived(actived);
		studentDocumentBean.setStudentId(studentId);
		studentDocumentBean.setSource(SourceType.USER_UPLOAD);
		Page pageResult = this.studentDocumentService.pagePersonalDocumentByItem(studentDocumentBean, page, pageSize);
		resultBean.setBody(pageResult);
		return resultBean;
	}

	/**
	 * 我上传的文档
	 * @param page
	 * @param pageSize
	 * @return resultBean
	 */
	@ApiOperation(value = "我上传的文档，包含目录结构")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/myDir", method = RequestMethod.GET)
	public Object myDir(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(name = "dir", required = false) Long dirId) {
		CommonResultBean resultBean = new CommonResultBean();
		JSONObject result = new JSONObject();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		Byte actived = 1;
		studentDocumentBean.setActived(actived);
		studentDocumentBean.setStudentId(studentId);
		studentDocumentBean.setDirectoryId(dirId);
		studentDocumentBean.setSource(SourceType.USER_UPLOAD);
		result.put("doc", studentDocumentService.personalDocumentDir(studentDocumentBean, page, pageSize));
		if (page <= 1) {
			Conds conds = new Conds();
			conds.equal("parent_id", dirId);
			conds.equal("student_id", studentId);
			conds.equal("actived", actived);
			result.put("dir", directoryService.fetchPageSearch(conds, null, 0, 0));
		}
		resultBean.setBody(result);
		return resultBean;
	}

	/**
	 * 我的收藏的文档
	 *
	 * @param page     当前页
	 * @param pageSize 每页显示条数
	 * @return resultBean
	 */
	@ApiOperation(value = "我收藏的文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/marked", method = { RequestMethod.GET })
	public Object marked(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		MarkRecordBean markRecordBean = new MarkRecordBean();
		markRecordBean.setActived(SuimeLibraryConstants.COMMON_ACTIVED_VALID);
		markRecordBean.setStudentId(studentId);
		Page pageResult = this.markRecordService.pageMarkDocumentByItem(markRecordBean, page, pageSize);
		resultBean.setBody(pageResult);
		return resultBean;
	}

	/**
	 * 我的文档，包括上传和收藏
	 * @param page
	 * @param pageSize
	 * @param sortField averageScore
	 * @param sort
	 * @return resultBean
	 */
	@ApiOperation(value = "我的文档，包括上传和收藏")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/myAll", method = { RequestMethod.GET })
	public Object myall(@RequestParam(name = "page", defaultValue = "0") Integer page, @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(name = "sortField", defaultValue = "createdAt") String sortField, @RequestParam(name = "sort", defaultValue = "DESC") String sort) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
		Byte actived = 1;
		Long userId = BaseUserHelper.getInstance().getUserId();
		studentDocumentBean.setActived(actived);
		studentDocumentBean.setStudentId(userId);
		studentDocumentBean.setSource(SourceType.USER_UPLOAD);
		OrderType order = OrderType.valueOf(sort);
		Page resultPage = this.studentDocumentService.pagePersonalDocumentAll(studentDocumentBean, page, pageSize, sortField, order);
		if (page.intValue() < 2) {
			List<StudentDocumentDto> list = stdocStickRemovedService.fetchStickDocumentWithoutRemoved(userId);
			if (list != null) {
				ArrayList<StudentDocumentDto> data = (ArrayList<StudentDocumentDto>) resultPage.getData();
				data.addAll(0, list);
			}
		}
		resultBean.setBody(resultPage);
		return resultBean;
	}

	/**
	 * 我公开的文档
	 * @param page
	 * @param pageSize
	 * @return resultBean
	 */
	@ApiOperation(value = "我公开的文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/myPublic", method = RequestMethod.GET)
	public Object myPublic(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		Byte actived = 1;
		studentDocumentBean.setActived(actived);
		studentDocumentBean.setStudentId(studentId);
		studentDocumentBean.setPermission(PermissionType.PERMISSION_PUBLIC);
		studentDocumentBean.setSource(SourceType.USER_UPLOAD);
		Page pageResult = this.studentDocumentService.pagePersonalDocumentByItem(studentDocumentBean, page, pageSize);
		resultBean.setBody(pageResult);
		return resultBean;
	}

	/**
	 * 我收到的文档
	 * @param page
	 * @param pageSize
	 * @return resultBean
	 */
	@ApiOperation(value = "发送给我的文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/myReceived", method = RequestMethod.GET)
	public Object myReceived(@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(this.sendRecordService.pageReceivedDocument(studentId, page, pageSize));
		return resultBean;
	}

	/**
	 * 相关推荐文档，后续产品中废弃该接口
	 * @return resultBean
	 */
	@ApiOperation(value = "相关推荐文档，后续产品中废弃该接口")
	@RequestMapping(path = "/recommend/{id}", method = { RequestMethod.GET })
	@Deprecated
	public Object recommend(@PathVariable("id") Long id, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long userId = BaseUserHelper.getInstance().getUserId();
		List<StudentDocumentDto> list = docRecommendService.fetchRecommendDocuments(id, userId, pageSize);
		resultBean.setBody(list);
		return resultBean;
	}

	/**
	 * 首页推荐
	 * @return resultBean
	 */
	@ApiOperation(value = "首页推荐文档，根据收藏，评分，打印推荐文档")
	@RequestMapping(path = "/recommend", method = { RequestMethod.GET })
	public Object recommend(@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {

		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

		Object recommendStr = cacheService.get(KEY_INDEX_BY_RECOMMEND);
		final int exp = 60 * 60 * 24; // 一天
		if (recommendStr == null) {

			JSONObject result = new JSONObject();
			result.put("marks", studentDocumentService.fetchRecommendDocuments(null, OrderFieldEnum.MARK_COUNT, pageSize));
			result.put("ratings", studentDocumentService.fetchRecommendDocuments(null, OrderFieldEnum.RATINGS, pageSize));
			result.put("prints", studentDocumentService.fetchRecommendDocuments(null, OrderFieldEnum.PRINT_COUNT, pageSize));
			resultBean.setBody(result);
			cacheService.set(KEY_INDEX_BY_RECOMMEND, exp, JsonUtil.toJsonString(result));
		} else {
			resultBean.setBody(JsonUtil.toJsonObject(recommendStr));
		}
		return resultBean;
	}

	/**
	 * ios 移动端请求的 首页显示的类别
	 *
	 * @return resultBean
	 */
	@ApiOperation(value = "ios 移动端请求的 首页显示的类别")
	@RequestMapping(path = "/menu", method = { RequestMethod.GET })
	public Object menu() {
		CategoryBean categoryBean = new CategoryBean();
		Byte actived = 1;
		categoryBean.setActived(actived);
		List<CategoryDto> list = categoryService.findDtoByItem(categoryBean, false);
		JSONArray jsonArray = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CategoryDto dto : list) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("text", dto.getName());
				jsonObject.put("id", dto.getId());
				jsonObject.put("valueKey", "category" + dto.getId());
				jsonArray.add(jsonObject);
			}
		}
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(jsonArray);
		return resultBean;
	}

	/**
	 * 获取下载地址
	 *
	 * @return
	 */
	@ApiOperation(value = "ios 获取下载地址")
	@RequiresAuthentication
	@RequestMapping(path = "/getLoadUrl/{id}", method = { RequestMethod.POST })
	public Object getLoadUrl(@PathVariable("id") Long id) {
		StudentDocument studentDocument = getStudentDocument(id);

		String key = studentDocument.getFileKey();
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		int minutes = 60;
		String fileName = studentDocument.getFileName();
		resultBean.setBody(AliyunOSSClientHelper.getInstance().generateGetPresignedUrl(key, fileName, minutes));
		return resultBean;
	}

	/**
	 * 获取studentDocument
	 * @param id
	 * @return studentDocument
	 */
	private StudentDocument getStudentDocument(@PathVariable("id") Long id) {
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		StudentDocument studentDocument = studentDocumentService.fetchById(id);
		if (studentDocument == null || studentDocument.getActived().intValue() != 1) {
			throw BusinessErrors.getInstance().fileNotFound();
		}
		boolean flagSelfDocument = studentDocument.getStudentId().longValue() == studentId.longValue();
		logger.info("studentDocument's studentId:" + studentDocument.getStudentId() + ",loginStudentId:" + studentId);
		if (studentDocument.getPermission().intValue() == 3 && !flagSelfDocument) {
			throw BusinessErrors.getInstance().fileNoAuth();
		}
		if (studentDocument.getReviewState().intValue() != 2 && !flagSelfDocument) {
			throw BusinessErrors.getInstance().fileNoAuth();
		}
		return studentDocument;
	}

	// /**
	// * android 获取下载地址
	// * @return resultBean
	// */
	// @ApiOperation(value = "android 获取下载地址")
	// @RequiresAuthentication
	// @RequestMapping(path = "/getM2LoadUrl/{id}", method = { RequestMethod.POST })
	// public Object getAndroidLoadUrl(@PathVariable("id") Long id, @RequestParam(name = "type", defaultValue = "1") Integer type) {
	// String key = "";
	// String fileName = "";
	//
	// Long studentId = BaseUserHelper.getInstance().getUserId();
	// if (studentId == null) {
	// throw BusinessErrors.getInstance().userOfflineError();
	// }
	// if (type == 2) {
	// PrintfilePrintshopRef printfilePrintshopRef = printfilePrintshopRefService.fetchById(id);
	// if (printfilePrintshopRef == null || printfilePrintshopRef.getActive().intValue() != 1) {
	// throw BusinessErrors.getInstance().fileNotFound();
	// }
	// if (printfilePrintshopRef == null || printfilePrintshopRef.getId() == null || printfilePrintshopRef.getFileId() == null) {
	// throw BusinessErrors.getInstance().fileNotFound();
	// }
	// Long fileId = printfilePrintshopRef.getFileId();
	// File file = fileService.fetchById(fileId);
	// if (file == null || file.getId() == null) {
	// throw BusinessErrors.getInstance().fileNotFound();
	// }
	// key = file.getKey();
	// fileName = printfilePrintshopRef.getName();
	// } else if (type == 1) {
	// StudentDocument studentDocument = getStudentDocument(id);
	// key = studentDocument.getFileKey();
	// fileName = studentDocument.getFileName();
	// } else {
	// throw BusinessErrors.getInstance().fileNotFound();
	// }
	// if (!StringUtils.contains(key, "-pdf-")) {
	// key += "-pdf";
	// }
	// CommonResultBean resultBean = new CommonResultBean();
	// resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
	// int minutes = 60;
	// String nameWithoutExtension = FileTypeUtil.getNameWithoutExtension(fileName) + ".pdf";
	// resultBean.setBody(AliyunOSSClientHelper.getInstance().generateGetPresignedUrl(key, nameWithoutExtension, minutes));
	// return resultBean;
	// }

	/**
	 * android 获取下载地址
	 *
	 * @return
	 */
	@ApiOperation(value = "android 获取下载地址")
	@RequiresAuthentication
	@RequestMapping(path = "/getM2LoadUrl/{id}", method = { RequestMethod.POST })
	public Object getAndroidLoadUrl(@PathVariable("id") Long id) {
		StudentDocument studentDocument = getStudentDocument(id);

		String key = studentDocument.getFileKey();
		if (!StringUtils.contains(key, "-pdf-")) {
			key += "-pdf";
		}
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		int minutes = 60;
		String fileName = studentDocument.getFileName();
		String nameWithoutExtension = FileTypeUtil.getNameWithoutExtension(fileName) + ".pdf";
		resultBean.setBody(AliyunOSSClientHelper.getInstance().generateGetPresignedUrl(key, nameWithoutExtension, minutes));
		return resultBean;
	}

	/**
	 * 最近操作的文档
	 * @return resultBean
	 */
	@ApiOperation(value = "最近操作的文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/recentOperation", method = RequestMethod.GET)
	public Object recentOperation(@RequestParam(name = "page", defaultValue = "1") Integer curPage,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Long studentId = BaseUserHelper.getInstance().getUserId();
		if (studentId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		int tempPageSize = pageSize;
		if (tempPageSize > 100) {
			tempPageSize = 100;
		}
		resultBean.setBody(operationRecordService.recentOperation(studentId, curPage, tempPageSize));
		return resultBean;
	}

}

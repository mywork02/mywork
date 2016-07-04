/**
 * DocumentManagementController.java
 * 2015年11月30日
 */
package com.suime.library.rest.admin;

import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.dto.pack.AliyunOSSBean;
import com.suime.common.error.BusinessErrors;
import com.suime.common.helper.AliyunOSSClientHelper;
import com.suime.common.helper.ThumbnailAliyunOSSHelper;
import com.suime.constants.PermissionType;
import com.suime.constants.RoleType;
import com.suime.context.model.File;
import com.suime.context.model.School;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.library.dto.pack.SearchAdminBean;
import com.suime.library.dto.pack.StudentDocumentBean;
import com.suime.library.service.FileService;
import com.suime.library.service.SchoolService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.service.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * DocumentManagementController
 * 
 * @author origin
 */
@Api(description = "文档管理后台接口", tags = "docManagement")
@RequiresRoles(value = { RoleType.SUPER_ADMIN, RoleType.SUPER_WENKU,
		RoleType.WENKU_ADMIN }, logical = Logical.OR)
@RestController(value = "adminDocManagementRestController")
@RequestMapping("/docManagement")
public class DocManagementRestController extends AbstractRestController {

	/**
	 * studentDocumentService
	 */
	@Autowired
	private StudentDocumentService studentDocumentService;

	/**
	 * fileService
	 */
	@Autowired
	private FileService fileService;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SchoolService schoolService;

	/**
	 * 下架单个文档
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "下架单个文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/remove", method = { RequestMethod.DELETE })
	public Object remove(@RequestParam("id") Long id) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Byte reviewState = 1;
		this.studentDocumentService.changeReviewStateById(id, reviewState);
		return resultBean;
	}

	/**
	 * 批量下架
	 * 
	 * @param idList
	 * @return
	 */
	@ApiOperation(value = "批量下架文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/removeList", method = { RequestMethod.DELETE })
	public Object removeByList(@RequestParam(name = "ids") List<Long> idList) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Byte reviewState = 1;
		this.studentDocumentService
				.changeReviewStateByList(idList, reviewState);
		return resultBean;
	}

	/**
	 * 上架单个文档
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "上架单个文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/shelve/{id}", method = { RequestMethod.POST })
	public Object shelve(@PathVariable("id") Long id) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Byte reviewState = 2;
		this.studentDocumentService.changeReviewStateById(id, reviewState);
		return resultBean;
	}

	/**
	 * 批量上架文档
	 * 
	 * @param idList
	 * @return
	 */
	@ApiOperation(value = "批量上架文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/shelveList", method = { RequestMethod.POST })
	public Object shelveByList(@RequestBody List<Long> idList) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		Byte reviewState = 2;
		this.studentDocumentService
				.changeReviewStateByList(idList, reviewState);
		return resultBean;
	}

	/**
	 * 分页获取上架文档
	 * 
	 * @param page
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @param sort
	 *            排序方式
	 * @param sortField
	 *            排序字段:平均分用averageScore
	 * @return
	 */
	@ApiOperation(value = "分页获取上架文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/listShelves", method = { RequestMethod.GET })
	public Object listShelves(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(name = "cellphone", required = false) String stuPhone,
			@RequestParam(name = "schoolId",required = false) Long schoolId,
			@RequestParam(name = "sortField", defaultValue = "createdAt") String sortField,
			@RequestParam(name = "sort", defaultValue = "DESC") String sort) {
		Long studentId = null;
		if (StringUtils.isNotBlank(stuPhone)) {
			Conds conds = new Conds();
			conds.equal("cellphone", stuPhone);
			Student stu = studentService.fetchSearchByConds(conds);
			if (stu != null) {
				studentId = stu.getId();
			}
		}
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
		if (studentId != null) {
			studentDocumentBean.setStudentId(studentId);
		}
		if (schoolId!=null) {
			studentDocumentBean.setSchoolId(schoolId);
		}
		Byte reviewState = 2;
		OrderType order = OrderType.valueOf(sort);
		studentDocumentBean.setReviewState(reviewState);

		Conds conds = new Conds();
		conds.equal("sd.actived", 1);
		conds.equal("sd.review_state", reviewState);
		resultBean.setBody(this.studentDocumentService.pageAllDocumentByItem(
				studentDocumentBean, page, pageSize, sortField, order));
		return resultBean;
	}

	/**
	 * 分页获取待上架文档
	 * 
	 * @param page
	 *            当前页
	 * @param pageSize
	 *            每页大小
	 * @param sort
	 *            排序方式
	 * @param sortField
	 *            排序字段:平均分用averageScore
	 * @return
	 */
	@ApiOperation(value = "分页获取待上架文档")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/listWaiting", method = { RequestMethod.GET })
	public Object listWaiting(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(name = "sortField", defaultValue = "createdAt") String sortField,
			@RequestParam(name = "schoolId",required = false) Long schoolId,
			@RequestParam(name = "cellphone", required=false) String stuPhone,
			@RequestParam(name = "sort", defaultValue = "DESC") String sort) {
		Long studentId = null;
		if (StringUtils.isNotBlank(stuPhone)) {
			Conds conds = new Conds();
			conds.equal("cellphone", stuPhone);
			Student stu = studentService.fetchSearchByConds(conds);
			if (stu != null) {
				studentId = stu.getId();
			}
		}
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
		if(studentId!=null){
			studentDocumentBean.setStudentId(studentId);
		}
		if (schoolId!=null) {
			studentDocumentBean.setSchoolId(schoolId);
		}
		Byte reviewState = 1;
		studentDocumentBean.setReviewState(reviewState);
		OrderType order = OrderType.valueOf(sort);
		resultBean.setBody(this.studentDocumentService.pageAllDocumentByItem(
				studentDocumentBean, page, pageSize, sortField, order));
		return resultBean;
	}

	/**
	 * 更新文档信息
	 * 
	 * @param studentDocumentBean
	 * @return
	 */
	@ApiOperation(value = "更新文档信息")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/update", method = { RequestMethod.POST })
	public Object updateStudentDoc(
			@RequestBody StudentDocumentBean studentDocumentBean) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		if (studentDocumentBean == null || studentDocumentBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		StudentDocument studentDocument = this.studentDocumentService
				.fetchById(studentDocumentBean.getId());
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().fileNotFound();
		}
		if (studentDocument.getPermission() != PermissionType.PERMISSION_PUBLIC) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		studentDocumentBean.setPermission(null);
		this.studentDocumentService.updateStudentDocument(studentDocumentBean);
		return resultBean;
	}

	/**
	 * 搜索文档
	 * 
	 * @param studentDocumentName
	 * @param studentNickName
	 * @param tag
	 * @param sortField
	 *            排序字段:平均分用averageScore
	 * @param sort
	 *            排序类型
	 * @param curPage
	 *            当前页
	 * @param pageSize
	 *            分页大小
	 * @param tagId
	 *            标签id，非必需
	 * @param categoryId
	 *            分类id，非必需
	 * @return
	 */
	@ApiOperation(value = "根据条件获取文档，条件为空时获取所有文档，text为文档名称")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/search", method = { RequestMethod.GET })
	public Object searchDoc(
			@RequestParam(name = "studentNickName", required = false) String studentNickName,
			@RequestParam(name = "text", required = false) String studentDocumentName,
			@RequestParam(name = "category", required = false) Long categoryId,
			@RequestParam(name = "tagId", required = false) Long tagId,
			@RequestParam(name = "tag", required = false) String tag,
			@RequestParam(name = "sortField", defaultValue = "readCount") String sortField,
			@RequestParam(name = "sort", defaultValue = "DESC") String sort,
			@RequestParam(name = "page", defaultValue = "1") Integer curPage,
			@RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		SearchAdminBean searchAdminBean = new SearchAdminBean();
		searchAdminBean.setSort(OrderType.valueOf(sort.toUpperCase()));
		searchAdminBean.setSortField(sortField);
		searchAdminBean.setCategoryId(categoryId);
		searchAdminBean.setTagId(tagId);
		searchAdminBean.setStudentDocumentName(studentDocumentName);
		searchAdminBean.setName(studentNickName);
		searchAdminBean.setTag(tag);
		Page result = this.studentDocumentService.adminSearchByItem(
				searchAdminBean, curPage, pageSize);
		resultBean.setBody(result);
		return resultBean;
	}

	/**
	 * 获取封面上传地址
	 * 
	 * @param request
	 * @return 封面上传地址
	 */
	@ApiOperation(value = "获取封面上传地址")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/uploadBegin", method = RequestMethod.GET)
	public Object beginCreate(HttpServletRequest request) {
		String imageKey = "thumbnail-" + UUID.randomUUID().toString();
		final int minutes = 20;// 20分钟
		URL url = ThumbnailAliyunOSSHelper.getInstance()
				.generatePutPresignedUrl(imageKey, minutes);

		JSONObject result = new JSONObject();
		result.put("imageKey", imageKey);
		result.put("url", url.toString());

		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		resultBean.setBody(result);
		return resultBean;
	}

	/**
	 * 上传结束，获取封面访问地址,覆盖原有封面
	 * 
	 * @param imageKey
	 * @param studentDocumentId
	 * @return 封面访问地址
	 */
	@ApiOperation(value = "封面上传结束，获取封面访问地址,覆盖原有封面")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequiresAuthentication
	@RequestMapping(path = "/uploadEnd", method = RequestMethod.GET)
	public Object endCeate(@RequestParam("imageKey") String imageKey,
			@RequestParam("studentDocumentId") Long studentDocumentId) {
		String extension = "img";

		if (StringUtils.isBlank(imageKey) || studentDocumentId == null) {
			throw BusinessErrors.getInstance().paramsError();
		}

		StudentDocument studentDocument = this.studentDocumentService
				.fetchById(studentDocumentId);
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().paramsError();
		}

		ThumbnailAliyunOSSHelper thumbnailAliyunOSSHelper = ThumbnailAliyunOSSHelper
				.getInstance();
		AliyunOSSBean aliyunOSSBean = thumbnailAliyunOSSHelper
				.getObjectFromOSSByKey(imageKey, extension);
		if (aliyunOSSBean == null) {
			throw BusinessErrors.getInstance().fileFetchFailure();
		}
		String destinationKey = studentDocument.getFileKey() + "-thumbnail";
		String contentType = "image/jpeg";
		thumbnailAliyunOSSHelper.copyObject(imageKey, destinationKey,
				contentType);
		thumbnailAliyunOSSHelper.deleteObject(imageKey);
		File file = fileService.fetchById(studentDocument.getFileId());
		Byte hasThumbnail = 1;
		file.setHasThumbnail(hasThumbnail);
		fileService.update(file);
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		String visitUrl = thumbnailAliyunOSSHelper.getVisitUrl(destinationKey)
				+ "?" + DateUtil.getSqlTimestamp().getTime();
		resultBean.setBody(visitUrl);
		return resultBean;
	}

	/**
	 * 管理员 获取下载地址
	 * 
	 * @return
	 */
	@ApiOperation(value = "管理员 获取下载地址")
	@ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
	@RequestMapping(path = "/getLoadUrl/{id}", method = { RequestMethod.POST })
	public Object getLoadUrl(@PathVariable("id") Long id) {
		StudentDocument studentDocument = this.studentDocumentService
				.fetchById(id);
		String key = studentDocument.getFileKey();
		CommonResultBean resultBean = new CommonResultBean();
		resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		int minutes = 60;
		String fileName = studentDocument.getFileName();
		resultBean.setBody(AliyunOSSClientHelper.getInstance()
				.generateGetPresignedUrl(key, fileName, minutes));
		return resultBean;
	}
}

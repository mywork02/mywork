package com.suime.library.rest;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.SourceType;
import com.suime.context.model.Category;
import com.suime.context.model.School;
import com.suime.library.dto.pack.StudentDocumentAdminBean;
import com.suime.library.dto.pack.StudentDocumentBean;
import com.suime.library.service.CategoryService;
import com.suime.library.service.SchoolService;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.shiro.BaseUserHelper;

/**
 * uploadwithAdminRestController
 *
 * @author origin
 */
@RestController
@RequestMapping("/adminStdc")
public class UploadwithAdminRestController extends AbstractRestController {

	/**
	 * studentDocumentService
	 */
	@Autowired
	private StudentDocumentService studentDocumentService;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private CategoryService categoryService;

	@RequiresAuthentication
	@RequestMapping(path = "/add", method = { RequestMethod.POST })
	public Object add(@RequestBody List<StudentDocumentAdminBean> studentDocumentAdminBeans) {

		if (studentDocumentAdminBeans == null || studentDocumentAdminBeans.isEmpty()) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long userId = BaseUserHelper.getInstance().getUserId();
		this.logger.info("------------------userId:" + userId);
		if (userId == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		if (userId.intValue() != 21) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}

		Integer errorNumber = 0;
		Byte source = SourceType.COMPANY_UPLOAD;
		Byte recommended = 0;

		JSONArray jsonArray = new JSONArray();
		int index = 0;
		for (StudentDocumentAdminBean studentDocumentAdminBean : studentDocumentAdminBeans) {
			JSONObject tempJson = new JSONObject();
			StudentDocumentBean studentDocumentBean = new StudentDocumentBean();
			Long documentId = studentDocumentAdminBean.getDocumentId();
			tempJson.put("documentId", documentId);
			tempJson.put("index", index++);
			try {
				studentDocumentBean.setStudentId(userId);
				studentDocumentBean.setSource(source);
				studentDocumentBean.setRecommended(recommended);
				studentDocumentBean.setActived(studentDocumentAdminBean.getActived());
				studentDocumentBean.setPermission(studentDocumentAdminBean.getPermission());
				studentDocumentBean.setPrice(studentDocumentAdminBean.getPrice());
				studentDocumentBean.setIntro(studentDocumentAdminBean.getIntro());

				String tags = studentDocumentAdminBean.getTags();
				if (StringUtils.isNotBlank(tags)) {
					tags = tags.trim();
				} else {
					tags = "";
				}
				if (StringUtils.isBlank(studentDocumentAdminBean.getLevelFour()) && StringUtils.isNotBlank(studentDocumentAdminBean.getLevelFive())) {
					if (StringUtils.isNotBlank(tags)) {
						tags = tags.trim() + Constants.VALUE_SIMPLE_SPLIT_CHAR + studentDocumentAdminBean.getLevelFive().trim();
					} else {
						tags = studentDocumentAdminBean.getLevelFive().trim();
					}
				}
				studentDocumentBean.setTags(tags);

				studentDocumentBean.setName(studentDocumentAdminBean.getName());
				studentDocumentBean.setFileName(studentDocumentAdminBean.getFileName());
				studentDocumentBean.setDocumentId(studentDocumentAdminBean.getDocumentId());
				studentDocumentBean.setFileId(studentDocumentAdminBean.getFileId());
				studentDocumentBean.setMajorGroupId(studentDocumentAdminBean.getMajorGroupId());
				studentDocumentBean.setPreview(studentDocumentAdminBean.getPreview());
				studentDocumentBean.setReviewState(studentDocumentAdminBean.getReviewState());
				studentDocumentBean.setSchoolId(this.schoolNameToId(studentDocumentAdminBean.getSchoolName()));

				Category category = this.getCategory(studentDocumentAdminBean);
				if (category != null) {
					studentDocumentBean.setCategoryCode(category.getCode());
					studentDocumentBean.setCategoryId(category.getId());

					if (category.getMajorGroupId() != null) {
						studentDocumentBean.setMajorGroupId(category.getMajorGroupId());
					}
				}

				this.logger.info(studentDocumentAdminBean.toString());
				studentDocumentService.addStudentDocument(studentDocumentBean);
				tempJson.put("success", true);

			} catch (Exception e) {
				e.printStackTrace();
				errorNumber++;
				this.logger.error(e.getMessage());
				tempJson.put("success", false);
			}
			jsonArray.add(tempJson);
		}
		CommonResultBean resultBean = new CommonResultBean();
		if (errorNumber > 0) {
			resultBean.setResult(Constants.NORMAL_RESULT_ERROR);
		} else {
			resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
		}
		resultBean.setBody(jsonArray);
		return resultBean;
	}

	/**
	 * 寻找schoolName对应的Id
	 *
	 * @param name
	 * @return 对应id或null
	 */
	private Long schoolNameToId(String name) {
		if (StringUtils.isNotBlank(name)) {
			Conds conds = new Conds();
			conds.equal("name", name);
			School school = this.schoolService.fetchSearchByConds(conds);
			if (school == null) {
				return null;
			}
			return school.getId();
		}
		return null;
	}

	/**
	 * 获取category
	 *
	 * @param studentDocumentAdminBean
	 */
	private Category getCategory(StudentDocumentAdminBean studentDocumentAdminBean) {

		if (studentDocumentAdminBean == null) {
			return null;
		}
		Category category = null;
		String hql = null;

		if (StringUtils.isNotBlank(studentDocumentAdminBean.getLevelOne())) {
			Conds conds = new Conds();
			conds.equal("name", studentDocumentAdminBean.getLevelOne());
			conds.equal("level", 1);
			Category temp = this.categoryService.fetchSearchByConds(conds);
			if (temp == null) {
				category = temp;
			}
		} else {
			return category;
		}

		if (StringUtils.isNotBlank(studentDocumentAdminBean.getLevelTwo()) && category != null) {
			Conds conds = new Conds();
			conds.equal("name", studentDocumentAdminBean.getLevelTwo());
			conds.equal("level", 2);
			conds.equal("parent_id", category.getId());
			Category temp = this.categoryService.fetchSearchByConds(conds);
			if (temp == null) {
				category = temp;
			}
		} else {
			return category;
		}

		if (StringUtils.isNotBlank(studentDocumentAdminBean.getLevelThree()) && category != null) {
			Conds conds = new Conds();
			conds.equal("name", studentDocumentAdminBean.getLevelThree());
			conds.equal("level", 3);
			conds.equal("parent_id", category.getId());
			Category temp = this.categoryService.fetchSearchByConds(conds);
			if (temp == null) {
				category = temp;
			}
		} else {
			return category;
		}

		if (StringUtils.isNotBlank(studentDocumentAdminBean.getLevelFour()) && category != null) {
			Conds conds = new Conds();
			conds.equal("name", studentDocumentAdminBean.getLevelFour());
			conds.equal("level", 4);
			conds.equal("parent_id", category.getId());
			Category temp = this.categoryService.fetchSearchByConds(conds);
			if (temp == null) {
				category = temp;
			}
		} else {
			return category;
		}

		if (StringUtils.isNotBlank(studentDocumentAdminBean.getLevelFive()) && category != null) {
			Conds conds = new Conds();
			conds.equal("name", studentDocumentAdminBean.getLevelFive());
			conds.equal("level", 5);
			conds.equal("parent_id", category.getId());
			Category temp = this.categoryService.fetchSearchByConds(conds);
			temp.setMajorGroupId(category.getMajorGroupId());
			if (temp == null) {
				category = temp;
			}
		}

		return category;
	}
}

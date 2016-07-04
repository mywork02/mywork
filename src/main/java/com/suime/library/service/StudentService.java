package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.Student;
import com.suime.library.dto.pack.SearchAdminBean;

/**
 * studentService
 * Created by ice 17/02/2016.
 */
public interface StudentService extends GenericService<Student> {

	/**
	 * 根据cellphone和name模糊搜素
	 *
	 * @param searchAdminBean searchAdminBean
	 * @param page            page
	 * @param pageSize        pageSize
	 * @return page student
	 */
	Page searchByItem(SearchAdminBean searchAdminBean, int page, int pageSize);

	/**
	 * 根据buildingId获取楼长对应的用户
	 * @param buildingId
	 * @return student
	 */
	Student fetchPrintshopStudentByBuilding(Long buildingId);
}

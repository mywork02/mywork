package com.suime.library.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Student;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.pack.SearchAdminBean;
import com.suime.library.service.StudentService;

/**
 * studentService
 * Created by ice 17/02/2016.
 */
@Service("studentService")
public class StudentServiceImpl extends GenericServiceImpl<Student> implements StudentService {

	/**
	 * studentMapper
	 */
	@Autowired
	@Qualifier("studentMapper")
	private StudentMapper studentMapper;

	@Override
	public GenericMapper<Student> getGenericMapper() {
		return studentMapper;
	}

	@Override
	public Page searchByItem(SearchAdminBean searchAdminBean, int page, int pageSize) {
		if (searchAdminBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Conds conds = new Conds();

		if (StringUtils.isNotBlank(searchAdminBean.getName())) {
			conds.like("nick_name", searchAdminBean.getName());
		}
		if (StringUtils.isNotBlank(searchAdminBean.getCellphone())) {
			conds.like("cellphone", searchAdminBean.getCellphone());
		}
		if (searchAdminBean.getStudentStatus() != null) {
			conds.equal("status", searchAdminBean.getStudentStatus());
		}
		Sort sort = null;
		if (searchAdminBean.getSortField() != null) {
			sort = new Sort(searchAdminBean.getSortField(), searchAdminBean.getSort());
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		params.put("sort", sort);
		int count = this.count(conds);
		List<Student> list = this.studentMapper.fetchPageNoPassword(params);
		Page pageData = generatePage(page, pageSize, count, list);
		return pageData;
	}

	@Override
	public Student fetchPrintshopStudentByBuilding(Long buildingId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buildingId", buildingId);
		List<Student> students = studentMapper.fetchByBuilding(params);
		if (students == null || students.isEmpty()) {
			throw BusinessErrors.getInstance().paramsError();
		}
		return students.get(0);
	}

}

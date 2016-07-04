package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Student;

import java.util.List;
import java.util.Map;

/**
 * studentMapper
 * Created by ice 17/02/2016.
 */
public interface StudentMapper extends GenericMapper<Student> {
	/**
	 * 分页查询
	 *
	 * @param params
	 * @return list student
	 */
	List<Student> fetchPageNoPassword(Map<String, Object> params);

	/**
	 * 根据buildingId 获取楼长用户的信息
	 *
	 * @param params
	 * @return list student
	 */
	List<Student> fetchByBuilding(Map<String, Object> params);

}

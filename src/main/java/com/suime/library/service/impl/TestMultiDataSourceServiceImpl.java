package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.suime.context.model.Student;
import com.suime.library.dao.StudentMapper;
import com.suime.library.service.ITestMultiDataSourceService;

@Service("testMultiDataSourceService")
public class TestMultiDataSourceServiceImpl implements ITestMultiDataSourceService {

	/**
	 * studentMapper
	 */
	@Autowired
	@Qualifier("studentMapper")
	private StudentMapper studentMapper;

	@Override
	public void saveTest() {
		Student student1 = studentMapper.fetchById(21L);
		student1.setCash(200);
		studentMapper.update(student1);
		Student student2 = studentMapper.fetchById(22L);
	}

	@Override
	public void getTest() {
		Student student1 = new Student();
		student1.setId(21L);
		student1.setCash(200);
		studentMapper.update(student1);
		Student student2 = studentMapper.fetchById(22L);
	}

}

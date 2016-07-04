package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.SchoolMapper;
import com.suime.context.model.School;
import com.suime.library.service.SchoolService;

/**
 * schoolService
 * Created by ice 17/02/2016.
 */
@Service("schoolService")
public class SchoolServiceImpl extends GenericServiceImpl<School> implements SchoolService {

	/**
	 * schoolMapper
	 */
	@Autowired
	@Qualifier("schoolMapper")
	private SchoolMapper schoolMapper;

	@Override
	public GenericMapper<School> getGenericMapper() {
		return schoolMapper;
	}
}

package com.suime.library.service.impl;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.context.model.MajorGroup;
import com.suime.library.dao.MajorGroupMapper;
import com.suime.library.service.MajorGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * majorGroupService
 * Created by ice 17/02/2016.
 */
@Service("majorGroupService")
public class MajorGroupServiceImpl extends GenericServiceImpl<MajorGroup> implements MajorGroupService {

	/**
	 * majorGroupMapper
	 */
	@Autowired
	@Qualifier("majorGroupMapper")
	private MajorGroupMapper majorGroupMapper;

	@Override
	public GenericMapper<MajorGroup> getGenericMapper() {
		return majorGroupMapper;
	}
}

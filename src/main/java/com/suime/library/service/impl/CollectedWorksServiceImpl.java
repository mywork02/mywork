package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.CollectedWorksMapper;
import com.suime.context.model.CollectedWorks;
import com.suime.library.service.CollectedWorksService;

/**
 * collectedWorksService
 * Created by ice 26/05/2016.
 */
@Service("collectedWorksService")
public class CollectedWorksServiceImpl extends GenericServiceImpl<CollectedWorks> implements CollectedWorksService {

	/**
	 * collectedWorksMapper
	 */
	@Autowired
	@Qualifier("collectedWorksMapper")
	private CollectedWorksMapper collectedWorksMapper;

	@Override
	public GenericMapper<CollectedWorks> getGenericMapper() {
		return collectedWorksMapper;
	}
}

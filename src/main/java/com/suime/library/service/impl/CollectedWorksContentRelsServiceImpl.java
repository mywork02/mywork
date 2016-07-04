package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.CollectedWorksContentRelsMapper;
import com.suime.context.model.CollectedWorksContentRels;
import com.suime.library.service.CollectedWorksContentRelsService;

/**
 * collectedWorksContentRelsService
 * Created by ice 26/05/2016.
 */
@Service("collectedWorksContentRelsService")
public class CollectedWorksContentRelsServiceImpl extends GenericServiceImpl<CollectedWorksContentRels> implements CollectedWorksContentRelsService {

	/**
	 * collectedWorksContentRelsMapper
	 */
	@Autowired
	@Qualifier("collectedWorksContentRelsMapper")
	private CollectedWorksContentRelsMapper collectedWorksContentRelsMapper;

	@Override
	public GenericMapper<CollectedWorksContentRels> getGenericMapper() {
		return collectedWorksContentRelsMapper;
	}
}

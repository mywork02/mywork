package com.suime.library.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.CollectedWorks;
import com.suime.library.dao.CollectedWorksMapper;

/**
 * collectedWorksService
 * Created by ice 26/05/2016.
 */
@Repository("collectedWorksManager")
public class CollectedWorksManager extends GenericManager<CollectedWorks> {

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

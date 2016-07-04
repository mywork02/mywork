package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.StdocTagRelsMapper;
import com.suime.context.model.StdocTagRels;
import com.suime.library.service.StdocTagRelsService;

/**
 * stdocTagRelsService
 * Created by ice 13/03/2016.
 */
@Service("stdocTagRelsService")
public class StdocTagRelsServiceImpl extends GenericServiceImpl<StdocTagRels> implements StdocTagRelsService {

	/**
	 * stdocTagRelsMapper
	 */
	@Autowired
	@Qualifier("stdocTagRelsMapper")
	private StdocTagRelsMapper stdocTagRelsMapper;

	@Override
	public GenericMapper<StdocTagRels> getGenericMapper() {
		return stdocTagRelsMapper;
	}
}

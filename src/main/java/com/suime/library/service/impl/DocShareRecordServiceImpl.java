package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.DocShareRecordMapper;
import com.suime.context.model.DocShareRecord;
import com.suime.library.service.DocShareRecordService;

/**
 * docShareRecordService
 * Created by ice 21/06/2016.
 */
@Service("docShareRecordService")
public class DocShareRecordServiceImpl extends GenericServiceImpl<DocShareRecord> implements DocShareRecordService {

	/**
	 * docShareRecordMapper
	 */
	@Autowired
	@Qualifier("docShareRecordMapper")
	private DocShareRecordMapper docShareRecordMapper;

	@Override
	public GenericMapper<DocShareRecord> getGenericMapper() {
		return docShareRecordMapper;
	}
}

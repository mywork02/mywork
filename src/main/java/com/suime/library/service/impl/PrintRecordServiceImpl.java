package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.PrintRecordMapper;
import com.suime.context.model.PrintRecord;
import com.suime.library.service.PrintRecordService;

/**
 * printRecordService
 * Created by ice 17/02/2016.
 */
@Service("printRecordService")
public class PrintRecordServiceImpl extends GenericServiceImpl<PrintRecord> implements PrintRecordService {

	/**
	 * printRecordMapper
	 */
	@Autowired
	@Qualifier("printRecordMapper")
	private PrintRecordMapper printRecordMapper;

	@Override
	public GenericMapper<PrintRecord> getGenericMapper() {
		return printRecordMapper;
	}
}

package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.AppHitLogMapper;
import com.suime.context.model.AppHitLog;
import com.suime.library.service.AppHitLogService;

/**
 * appHitLogService
 * Created by ice 12/03/2016.
 */
@Service("appHitLogService")
public class AppHitLogServiceImpl extends GenericServiceImpl<AppHitLog> implements AppHitLogService {

	/**
	 * appHitLogMapper
	 */
	@Autowired
	@Qualifier("appHitLogMapper")
	private AppHitLogMapper appHitLogMapper;

	@Override
	public GenericMapper<AppHitLog> getGenericMapper() {
		return appHitLogMapper;
	}
}

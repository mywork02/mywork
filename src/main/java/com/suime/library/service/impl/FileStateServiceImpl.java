package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.FileStateMapper;
import com.suime.context.model.FileState;
import com.suime.library.service.FileStateService;

/**
 * fileStateService
 * Created by ice 17/02/2016.
 */
@Service("fileStateService")
public class FileStateServiceImpl extends GenericServiceImpl<FileState> implements FileStateService {

	/**
	 * fileStateMapper
	 */
	@Autowired
	@Qualifier("fileStateMapper")
	private FileStateMapper fileStateMapper;

	@Override
	public GenericMapper<FileState> getGenericMapper() {
		return fileStateMapper;
	}
}

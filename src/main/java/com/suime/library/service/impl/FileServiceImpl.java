package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.FileMapper;
import com.suime.context.model.File;
import com.suime.library.service.FileService;

/**
 * fileService
 * Created by ice 17/02/2016.
 */
@Service("fileService")
public class FileServiceImpl extends GenericServiceImpl<File> implements FileService {

	/**
	 * fileMapper
	 */
	@Autowired
	@Qualifier("fileMapper")
	private FileMapper fileMapper;

	@Override
	public GenericMapper<File> getGenericMapper() {
		return fileMapper;
	}
}

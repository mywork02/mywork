package com.suime.library.service.impl;

import com.suime.library.dao.FileLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.context.model.FileLink;
import com.suime.library.service.FileLinkService;

/**
 * filelinkService
 * Created by ice 17/02/2016.
 */
@Service("fileLinkService")
public class FileLinkServiceImpl extends GenericServiceImpl<FileLink> implements FileLinkService {

	/**
	 * fileLinkMapper
	 */
	@Autowired
	@Qualifier("fileLinkMapper")
	private FileLinkMapper fileLinkMapper;

	@Override
	public GenericMapper<FileLink> getGenericMapper() {
		return fileLinkMapper;
	}
}

package com.suime.library.service;

import java.util.List;
import java.util.Map;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.DirectoryContentRels;
import com.suime.library.dto.DirectoryContentDto;

/**
 * directoryContentRelsService
 * Created by ice 26/05/2016.
 */
public interface DirectoryContentRelsService extends GenericService<DirectoryContentRels> {
	
	/**
	 * 批量添加记录
	 * @param list
	 */
	void saveBatch(List<DirectoryContentRels> list);
	
	/**
	 * 分页获取带简介的文件夹及文件列表
	 */
	Page fetchSearchByPageIntro(Long studentId,Integer curPage,Integer pageSize,Long directoryId);
}

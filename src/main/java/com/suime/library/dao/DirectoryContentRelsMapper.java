package com.suime.library.dao;

import java.util.List;
import java.util.Map;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.DirectoryContentRels;
import com.suime.library.dto.DirectoryContentDto;

/**
 * directoryContentRelsMapper
 * Created by ice 26/05/2016.
 */
public interface DirectoryContentRelsMapper extends GenericMapper<DirectoryContentRels> {
	
	/**
	 * 批量添加记录
	 * @param list
	 */
	void saveBatch(List<DirectoryContentRels> list);
	
	/**
	 * 批量删除
	 */
	void removeBatch(Map<String, Object> maps);
	
	/**
	 * 批量更新
	 */
	void updateBatch(Map<String,Object> params);
	
	/**
	 * 分页获取带简介的文件夹及文档数量
	 */
	Integer countIntro(Map<String,Object> params);
	
	/**
	 * 分页获取带简介的文件夹及文档列表
	 */
	List<DirectoryContentDto> fetchSearchByPageIntro(Map<String,Object> params);
}

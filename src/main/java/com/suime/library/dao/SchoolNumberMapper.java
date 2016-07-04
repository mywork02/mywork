package com.suime.library.dao;

import java.util.List;
import java.util.Map;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.School;
import com.suime.context.model.SchoolNumber;
import com.suime.library.dto.SchoolNumberDto;

/**
 * schoolMapper
 * Created by ice 18/05/2016.
 * lsw
 */
public interface SchoolNumberMapper extends GenericMapper<SchoolNumber> {
	
	List<SchoolNumberDto> hotList(Map<String, Object> params);
	
	List<SchoolNumberDto> hotdocList(Map<String, Object> params);
	
	Integer hotdoccount();
}

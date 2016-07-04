package com.suime.library.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.context.model.SchoolNumber;
import com.suime.library.dao.SchoolNumberMapper;
import com.suime.library.dto.SchoolNumberDto;
import com.suime.library.service.SchoolNumberService;
@Service("schoolNumberService")
public class SchoolNumberServiceImpl extends GenericServiceImpl<SchoolNumber> implements SchoolNumberService {
	
	@Autowired
	@Qualifier("schoolNumberMapper")
	private SchoolNumberMapper schoolNumberMapper;


	@Override
	public List<SchoolNumberDto> findhotList(Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("limit",pageSize);
		return schoolNumberMapper.hotList(params);
	}

	@Override
	protected GenericMapper<SchoolNumber> getGenericMapper() {
		return schoolNumberMapper;
	}

	@Override
	public Page findhotdocList(int page,Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
        params.put("limit", pageSize > 0 ? pageSize : 0);
        int totalCount = schoolNumberMapper.hotdoccount();
        List<SchoolNumberDto> list =schoolNumberMapper.hotdocList(params);
		return generatePage(page, pageSize, totalCount, list);
	}
}

package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.SchoolNumber;
import com.suime.library.dto.SchoolNumberDto;

import java.util.List;

/**
 * schoolService
 * Created by ice 17/02/2016.
 */
public interface SchoolNumberService extends GenericService<SchoolNumber> {
	
	List<SchoolNumberDto> findhotList(Integer pageSize);
	

	Page findhotdocList(int page,Integer pageSize); 
	
	
	

}

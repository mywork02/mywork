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
import com.confucian.mybatis.support.sql.Conds;
import com.suime.constants.SuimeLibraryConstants;
import com.suime.context.model.OperationRecord;
import com.suime.library.dao.OperationRecordMapper;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.service.OperationRecordService;

/**
 * operationRecordService
 * Created by chenqy 20/04/2016.
 */
@Service("operationRecordService")
public class OperationRecordServiceImpl extends GenericServiceImpl<OperationRecord> implements OperationRecordService {

	/**
	 * operationRecordMapper
	 */
	@Autowired
	@Qualifier("operationRecordMapper")
	private OperationRecordMapper operationRecordMapper;

	@Override
	public GenericMapper<OperationRecord> getGenericMapper() {
		return operationRecordMapper;
	}

	@Override
	public Page recentOperation(Long studentId, Integer curPage, Integer pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("studentId", studentId);
		params.put("offset", curPage > 0 ? ((curPage - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		/*Conds conds = new Conds();
		conds.equal("student_id", studentId);
		conds.equal("actived", SuimeLibraryConstants.COMMON_ACTIVED_VALID);*/
		int count = operationRecordMapper.recentOperationCount(params);
		List<StudentDocumentDto> list = operationRecordMapper.recentOperation(params);
		return this.generatePage(curPage, pageSize, count, list);
	}
}

package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.OperationRecord;

/**
 * operationRecordService
 * Created by chenqy 20/04/2016.
 */
public interface OperationRecordService extends GenericService<OperationRecord> {

	/**
	 * 最近操作的文档
	 * @param studentId
	 * @param curPage
	 * @param pageSize
	 * @return page studentDocumentDto
	 */
	Page recentOperation(Long studentId, Integer curPage, Integer pageSize);
}

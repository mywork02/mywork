package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.SendRecord;
import com.suime.library.dto.SendRecordDto;
import com.suime.library.dto.StudentDocumentDto;

import java.util.List;
import java.util.Map;

/**
 * sendRecordMapper
 * Created by chenqy 07/04/2016.
 */
public interface SendRecordMapper extends GenericMapper<SendRecord> {

	/**
	 * 分页查询接收的文档
	 *
	 * @param params
	 * @return list student document dto
	 */
	List<StudentDocumentDto> receivedDocumentDto(Map<String, Object> params);

	/**
	 * count接收文档总数
	 *
	 * @param params
	 * @return count of reveived doc
	 */
	int countReceivedDocument(Map<String, Object> params);

	/**
	 * 分页获取记录
	 *
	 * @param params
	 * @return list send record dto
	 */
	List<SendRecordDto> fetchPageDto(Map<String, Object> params);
}

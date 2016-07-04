package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.OperationRecord;
import com.suime.library.dto.StudentDocumentDto;

import java.util.List;
import java.util.Map;

/**
 * operationRecordMapper
 * Created by chenqy 20/04/2016.
 */
public interface OperationRecordMapper extends GenericMapper<OperationRecord> {


    /**
     * 最近操作的文档
     *
     * @param params
     * @return list student document dto
     */
    List<StudentDocumentDto> recentOperation(Map<String, Object> params);
    
    /**
     * 最近操作的文档数量
     */
    Integer recentOperationCount(Map<String, Object> params);
}

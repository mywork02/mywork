package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.MarkRecord;
import com.suime.library.dto.MarkRecordDto;
import com.suime.library.dto.StudentDocumentDto;

import java.util.List;
import java.util.Map;

/**
 * markRecordMapper
 * Created by ice 17/02/2016.
 */
public interface MarkRecordMapper extends GenericMapper<MarkRecord> {

    /**
     * 分页查询 用户收藏的文档
     *
     * @param params
     * @return lsit student document dto
     */
    List<StudentDocumentDto> fetchMarkedStudentDocument(Map<String, Object> params);
    
    /**
     * 分页查询收藏夹下面的内容，包含文档后缀及文集
     *
     * @param params
     * @return lsit student document dto
     */
    List<MarkRecordDto> fetchMarkStudentDocumentPage(Map<String, Object> params);
    
    
    /**
     * 分页查询收藏夹下面的内容，包含文档后缀及文集（总数量）
     *
     * @param params
     * @return lsit student document dto
     */
    Integer fetchMarkStudentDocumentPageCount(Map<String, Object> params);

}

package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.ReadRecord;
import com.suime.library.dto.ReadRecordDto;

import java.util.List;
import java.util.Map;

/**
 * readRecordMapper
 * Created by ice 17/02/2016.
 */
public interface ReadRecordMapper extends GenericMapper<ReadRecord> {

    /**
     * 分页查询 dto
     *
     * @param params
     * @return list read record dto
     */
    List<ReadRecordDto> fetchDtoSearchByPage(Map<String, Object> params);

}

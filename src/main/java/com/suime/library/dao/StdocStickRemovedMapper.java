package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.StdocStickRemoved;

import java.util.List;
import java.util.Map;

/**
 * stdocStickRemovedMapper
 * Created by ice 14/03/2016.
 */
public interface StdocStickRemovedMapper extends GenericMapper<StdocStickRemoved> {

    /**
     * 获取删除的推荐文档的 studentDocumentId
     * @param params
     * @return list ids of removed  
     */
    List<Long> fetchRemovedStdocIds(Map<String, Object> params);
}

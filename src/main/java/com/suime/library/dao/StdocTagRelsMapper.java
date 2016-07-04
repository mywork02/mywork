package com.suime.library.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.StdocTagRels;

/**
 * stdocTagRelsMapper
 * Created by ice 13/03/2016.
 */
public interface StdocTagRelsMapper extends GenericMapper<StdocTagRels> {

    /**
     * 删除对象
     *
     * @param stdocId 文档id
     * @param tagIds  标签ids
     * @return count of removed
     */
    int removeRelsByTagIds(@Param("stdocId") Long stdocId, @Param("tagIds") List<Long> tagIds);

    /**
     * 获取文档所有标签
     *
     * @param stdocId 文档id
     * @return list tag's id
     */
    List<Long> fetchTagIdByStdocId(@Param("stdocId") Long stdocId);

    /**
     * 测试查询
     *
     * @param stdocId 文档id
     * @param tagIds  标签ids
     * @return list stdoc tag rels
     */
    List<StdocTagRels> fetchByTagIds(@Param("stdocId") Long stdocId, @Param("tagIds") List<Long> tagIds);

}

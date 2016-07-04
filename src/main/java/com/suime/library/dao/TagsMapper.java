package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Tags;
import com.suime.library.dto.TagsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * tagsMapper
 * Created by ice 15/02/2016.
 */
public interface TagsMapper extends GenericMapper<Tags> {

    /**
     * 获取类别所包含的所有标签
     *
     * @param params
     * @return list tagsDto
     */
    List<TagsDto> fetchDtoSearchByPage(Map<String, Object> params);

    /**
     * 根据类别id,获取类别所包含的所有标签,仅有 id,source,text,useCount 属性
     *
     * @param params 参数:categoryId 类别id
     * @return list tagsDto
     */
    List<TagsDto> fetchDto(Map<String, Object> params);

    /**
     * 获取用壶上传的文档中包含有的tags,仅有 id,source,text,useCount 属性
     *
     * @param params 参数:categoryId 类别id
     * @return list tagsDto
     */
    List<TagsDto> fetchDtoByDocStudent(Map<String, Object> params);

    /**
     * 更新useCount
     *
     * @param id    tagsId
     * @param value 增加值
     * @return updated 条数
     */
    int updateUseCount(@Param("id") Long id, @Param("value") Integer value);
    
    List<TagsDto> fetchDtoByDissertation();

}

package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.DocRecommend;
import com.suime.library.dto.StudentDocumentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * docRecommendMapper
 * Created by ice 29/04/2016.
 */
public interface DocRecommendMapper extends GenericMapper<DocRecommend> {

    /**
     * 根据用户id 获取推荐文档的id 集合
     *
     * @param userId      用户id
     * @param documentIds 推荐的文档ids
     * @param limit 返回条数
     * @return 文档ids
     */
    List<Long> fetchRecommendIds(@Param("userId") Long userId, @Param("documentIds") String documentIds, @Param("limit") Integer limit);

    /**
     * 获取符合条件的推荐文档
     *
     * @param params
     * @return list student document dto
     */
    List<StudentDocumentDto> fetchStDocDtoSearch(Map<String, Object> params);

}

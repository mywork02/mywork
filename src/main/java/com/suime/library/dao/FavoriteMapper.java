package com.suime.library.dao;

import java.util.List;
import java.util.Map;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Favorite;
import com.suime.library.dto.FavoriteDto;
import com.suime.library.dto.StudentDocumentDto;

/**
 * favoriteMapper
 * Created by chenqy 20/04/2016.
 */
public interface FavoriteMapper extends GenericMapper<Favorite> {


    /**
     * 根据条件获取收藏夹
     *
     * @param params
     * @return lsit favorite dto
     */
    List<FavoriteDto> fetchDtoByItem(Map<String, Object> params);

    /**
     * 删除收藏夹下的内容
     *
     * @param id
     */
    void removeInFavorite(Long id);
    
    
    /**
     * 获取收藏夹下面的文档
     */
    List<StudentDocumentDto> fetchfavoritedoc(Map<String,Object> params);
    
    /**
     *收藏夹下面的总文档数量 
     */
    Integer fetchCount(Long id);
    
    /**
     * 将文档加入到收藏夹
     */
    void updateDocToFavorite(Long studentId,Long documentId,Long favoriteId);
    
    
    /**
     * @param params
     * @return
     */
    Integer fetchDtoCount(Map<String, Object> params);
}

package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.Favorite;
import com.suime.library.dto.FavoriteDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.FavoriteBean;

import java.util.List;
import java.util.Map;

/**
 * favoriteService
 * Created by chenqy 20/04/2016.
 */
public interface FavoriteService extends GenericService<Favorite> {

    /**
     * 添加收藏夹
     *
     * @param favoriteBean
     */
    FavoriteDto add(FavoriteBean favoriteBean);

    /**
     * 删除收藏夹
     *
     * @param id
     * @param studentId
     */
    void remove(Long id, Long studentId);

    /**
     * 更新收藏夹
     *
     * @param favoriteBean
     */
    FavoriteDto updateByItem(FavoriteBean favoriteBean);

    /**
     * 查看个人收藏夹
     *
     * @param studentId
     * @return
     */
    Page fetchPersonal(Long studentId,int page,int pageSize);
    
    /**
     * 获取收藏夹下面的文档
     */
    Page fetchfavoritedoc(Long favoriteId,Long studentId,int page,int pageSize);
    
    /**
     * 
     * 将文档加入到收藏夹下
     */
    void updateDocToFavorite(Long studentId,Long documentId,Long favoriteId);
    
    /**
     * 将文档移除收藏夹
     */
    void removedoc(Long studentId,Long documentId,Long favoriteId);
    


}

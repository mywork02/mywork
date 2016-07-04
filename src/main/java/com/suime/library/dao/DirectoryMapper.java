package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Directory;
import com.suime.library.dto.DirectoryDto;

import java.util.List;
import java.util.Map;

/**
 * directoryMapper
 * Created by ice 12/03/2016.
 */
public interface DirectoryMapper extends GenericMapper<Directory> {

    /**
     * 查找子目录id
     *
     * @param id
     * @return list directory
     */
    List<Directory> fetchSubdirectoryIds(Long id);

    /**
     * 批量删除目录
     *
     * @param params
     * @return batch removed count
     */
    int removeBatch(Map<String, Object> params);

    /**
     * 查询 返回 dto
     *
     * @param params
     * @return list directory dto
     */
    List<DirectoryDto> fetchDtoSearchByPage(Map<String, Object> params);
    
    /**
     * 批量把目录移动到某目录下面
     * @param params
     * @return
     */
    int updateDoresMove(Map<String,Object> params);
    
    /**
     *批量修改目录权限
     *@param params
     */
    void changePermission(Map<String,Object> params);
    
    /**
    * 
    */
    DirectoryDto fetchDtoSeachById(Map<String,Object> params);

}

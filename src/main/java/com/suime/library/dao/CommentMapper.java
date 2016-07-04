package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Comment;
import com.suime.library.dto.CommentDto;

import java.util.List;
import java.util.Map;

/**
 * commentMapper
 * Created by ice 17/02/2016.
 */
public interface CommentMapper extends GenericMapper<Comment> {

    /**
     * 分页查询 dto
     *
     * @param params params
     * @return list commentDto
     */
    List<CommentDto> fetchDtoSearchByPage(Map<String, Object> params);

    /**
     * 更新对象
     *
     * @param params params
     * @return int 更新条数
     */
    int batchRemove(Map<String, Object> params);

}

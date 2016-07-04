package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.Comment;
import com.suime.library.dto.CommentDto;
import com.suime.library.dto.pack.CommentBean;

import java.util.List;

/**
 * commentService
 * Created by ice 17/02/2016.
 */
public interface CommentService extends GenericService<Comment> {

    /**
     * add docComment
     *
     * @param commentBean commentBean
     * @return commentDto
     */
    CommentDto addComment(CommentBean commentBean);

    /**
     * pageByItem
     *
     * @param commentBean commentBean
     * @param page        page
     * @param pageSize    pageSize
     * @return page commentDto
     */
    Page pageByItem(CommentBean commentBean, int page, int pageSize);

    /**
     * pageByItem
     *
     * @param commentBean commentBean
     * @param page        page
     * @param pageSize    pageSize
     * @return page commentDto
     */
    Page pageByItemForMobile(CommentBean commentBean, int page, int pageSize);

    /**
     * 根据学生手机号获取评论，手机号为空时获取所有评论
     *
     * @param cellphone 手机号
     * @param text      内容
     * @param page      page
     * @param pageSize  pageSize
     * @return page comment
     */
    Page pageByStudentOrContent(String cellphone, String text, int page, int pageSize);

    /**
     * 软删除
     *
     * @param id id
     */
    void removeById(Long id);

    /**
     * 批量软删除
     *
     * @param ids ids
     */
    void removeByIdList(List<Long> ids);

}

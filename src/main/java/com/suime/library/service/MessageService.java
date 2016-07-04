package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.context.model.Message;

/**
 * messageService
 * Created by ice 14/03/2016.
 */
public interface MessageService extends GenericService<Message> {

    /**
     * 分页查询dto,返回封装好的page
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return page
     */
    Page fetchDtoPageSearch(Conds conds, Sort sort, int page, int pageSize);

    /**
     * 根据接收人id 标记所有未读记录为已读
     *
     * @param receiverId
     * @return
     */
    Integer update2ReadedByReceiverId(Long receiverId);

}

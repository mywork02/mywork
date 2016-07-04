package com.suime.library.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Message;
import com.suime.library.dto.MessageDto;

/**
 * messageMapper
 * Created by ice 14/03/2016.
 */
public interface MessageMapper extends GenericMapper<Message> {

    /**
     * 分页查询dto
     *
     * @param params
     * @return list message dto
     */
    List<MessageDto> fetchDtoSearchByPage(Map<String, Object> params);

    /**
     * 根据接收人id 标记所有未读记录为已读
     *
     * @param receiverId receiver(student) id
     * @return received count
     */
    int readAllByReceiverId(@Param("receiverId") Long receiverId);

}

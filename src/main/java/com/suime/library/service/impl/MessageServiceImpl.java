package com.suime.library.service.impl;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.library.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.MessageMapper;
import com.suime.context.model.Message;
import com.suime.library.service.MessageService;

import java.util.List;
import java.util.Map;

/**
 * messageService
 * Created by ice 14/03/2016.
 */
@Service("messageService")
public class MessageServiceImpl extends GenericServiceImpl<Message> implements MessageService {

    /**
     * messageMapper
     */
    @Autowired
    @Qualifier("messageMapper")
    private MessageMapper messageMapper;

    @Override
    public GenericMapper<Message> getGenericMapper() {
        return messageMapper;
    }

    @Override
    public Page fetchDtoPageSearch(Conds conds, Sort sort, int page, int pageSize) {
        int count = this.count(conds);
        Map<String, Object> params = this.generateParamsMap(conds, sort, page, pageSize);
        List<MessageDto> list = this.messageMapper.fetchDtoSearchByPage(params);
        return this.generatePage(page, pageSize, count, list);
    }

    @Override
    public Integer update2ReadedByReceiverId(Long receiverId) {
        return this.messageMapper.readAllByReceiverId(receiverId);
    }
}

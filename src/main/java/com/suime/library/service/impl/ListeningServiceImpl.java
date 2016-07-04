package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.ListeningMapper;
import com.suime.context.model.Listening;
import com.suime.library.service.ListeningService;

/**
 * listeningService
 * Created by ice 17/02/2016.
 */
@Service("listeningService")
public class ListeningServiceImpl extends GenericServiceImpl<Listening> implements ListeningService {

    /**
     * listeningMapper
     */
    @Autowired
    @Qualifier("listeningMapper")
    private ListeningMapper listeningMapper;

    @Override
    public GenericMapper<Listening> getGenericMapper() {
        return listeningMapper;
    }

    @Override
    public void updateTimeById(Long id) {
        listeningMapper.updateTimeById(id);
    }
}

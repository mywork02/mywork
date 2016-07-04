package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.Listening;

/**
 * listeningService
 * Created by ice 17/02/2016.
 */
public interface ListeningService extends GenericService<Listening> {

    /**
     * 访问次数+1
     * @param id
     */
    void updateTimeById(Long id);

}

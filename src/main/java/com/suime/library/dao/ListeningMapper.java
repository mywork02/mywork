package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Listening;

import java.io.Serializable;

/**
 * listeningMapper
 * Created by ice 17/02/2016.
 */
public interface ListeningMapper extends GenericMapper<Listening> {

    /**
     * 更新该mp3 的邵扫描次数
     * @param id
     * @return
     */
    int updateTimeById(Serializable id);

}

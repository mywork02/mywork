package com.suime.library.manager;

import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.StdocTagRels;
import com.suime.library.dao.StdocTagRelsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by ice on 14/3/2016.
 */
@Repository("stdocTagRelsManager")
public class StdocTagRelsManager extends GenericManager<StdocTagRels> {

    /**
     * stdocTagRelsMapper
     */
    @Autowired
    @Qualifier("stdocTagRelsMapper")
    private StdocTagRelsMapper stdocTagRelsMapper;

    @Override
    public GenericMapper<StdocTagRels> getGenericMapper() {
        return stdocTagRelsMapper;
    }
}

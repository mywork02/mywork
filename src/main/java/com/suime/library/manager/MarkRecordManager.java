package com.suime.library.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.MarkRecord;
import com.suime.library.dao.MarkRecordMapper;

/**
 * Created by ice on 12/3/2016.
 */
@Repository("markRecordManager")
public class MarkRecordManager extends GenericManager<MarkRecord> {

    /**
     * markRecordMapper
     */
    @Autowired
    @Qualifier("markRecordMapper")
    private MarkRecordMapper markRecordMapper;

    @Override
    protected GenericMapper<MarkRecord> getGenericMapper() {
        return markRecordMapper;
    }
}

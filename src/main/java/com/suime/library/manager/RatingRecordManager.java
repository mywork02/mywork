package com.suime.library.manager;

import com.confucian.mybatis.support.manager.GenericManager;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.RatingRecord;
import com.suime.library.dao.RatingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by ice on 12/3/2016.
 */
@Repository("ratingRecordManager")
public class RatingRecordManager extends GenericManager<RatingRecord> {

    /**
     * ratingRecordMapper
     */
    @Autowired
    @Qualifier("ratingRecordMapper")
    private RatingRecordMapper ratingRecordMapper;

    @Override
    protected GenericMapper<RatingRecord> getGenericMapper() {
        return ratingRecordMapper;
    }
}

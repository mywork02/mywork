package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.RatingRecord;
import com.suime.library.dto.RatingRecordDto;
import com.suime.library.dto.pack.RatingRecordBean;

/**
 * ratingRecordService
 * Created by ice 17/02/2016.
 */
public interface RatingRecordService extends GenericService<RatingRecord> {

    /**
     * addRatingRecord
     *
     * @param ratingRecordBean ratingRecordBean
     * @return printRecordDto
     */
    RatingRecordDto addRatingRecord(RatingRecordBean ratingRecordBean);

}

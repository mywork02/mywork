package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.ReadRecord;
import com.suime.library.dto.ReadRecordDto;
import com.suime.library.dto.pack.ReadRecordBean;

/**
 * readRecordService
 * Created by ice 17/02/2016.
 */
public interface ReadRecordService extends GenericService<ReadRecord> {

    /**
     * 添加学生浏览记录
     * @param readRecordBean 学生id
     * @return readRecordDto
     */
    ReadRecordDto addReadRecord(ReadRecordBean readRecordBean);

    /**
     * pageByItem
     *
     * @param readRecordBean readRecordBean
     * @param page           page
     * @param pageSize       pageSize
     * @return page readRecordDto
     */
    Page pageByItem(ReadRecordBean readRecordBean, int page, int pageSize);

}

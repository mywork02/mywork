package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.BlackListRecord;

/**
 * blackListRecordService
 * Created by ice 17/02/2016.
 */
public interface BlackListRecordService extends GenericService<BlackListRecord> {

    /**
     * 添加到黑名单
     *
     * @param id      id
     * @param adminId adminId
     */
    void addToBlackList(Long id, Long adminId);

    /**
     * 移除黑名单
     *
     * @param id      id
     * @param adminId adminId
     */
    void removeFromBlackList(Long id, Long adminId);

}

package com.confucian.framework.web.service;

/**
 * Created by ice on 23/11/2015.
 */
public interface LogsService {
    /**
     * 添加exception日志
     *
     * @param requestUrl
     * @param record
     * @param exception
     */
    void addExceptionLogs(String requestUrl, String record, String exception);
}

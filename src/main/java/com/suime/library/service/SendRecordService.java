package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.SendRecord;
import com.suime.library.dto.pack.SendRecordBean;

/**
 * sendRecordService
 * Created by chenqy 07/04/2016.
 */
public interface SendRecordService extends GenericService<SendRecord> {

    /**
     * 发送文档，添加新记录
     *
     * @param sendRecordBean
     */
    void addDocument(SendRecordBean sendRecordBean);

    /**
     * 发送文件夹
     *
     * @param sendRecordBean
     */
    void addDirectory(SendRecordBean sendRecordBean);

    /**
     * 接收文档
     *
     * @param id         发送记录id
     * @param receiverId 接收者id
     */
    void createReceive(Long id, Long receiverId);

    /**
     * 拒收文档
     *
     * @param id         发送记录id
     * @param receiverId 接收者id
     */
    void createRefuse(Long id, Long receiverId);

    /**
     * 接收的文档
     *
     * @param receiverId
     * @param page
     * @param pageSize
     * @return 分页page对象
     */
    Page pageReceivedDocument(Long receiverId, int page, int pageSize);

    /**
     * 分页获取记录
     *
     * @param receiverId
     * @param isAccepted 是否接收，1：接收，0：拒收，null：未处理
     * @param page
     * @param pageSize
     * @return 分页page对象 dto
     */
    Page fetchPageDto(Long receiverId, Byte isAccepted, int page, int pageSize);

}

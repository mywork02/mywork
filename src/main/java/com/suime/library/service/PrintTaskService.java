package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.PrintTask;
import com.suime.library.dto.PrintTaskDto;

/**
 * printTaskService
 * Created by ice 17/02/2016.
 */
public interface PrintTaskService extends GenericService<PrintTask> {

    /**
     * 计算未付款printTask
     *
     * @param studentId studentId
     * @return count
     */
    int countInCart(Long studentId);


    /**
     * 文档假如购物车
     *
     * @param studentId         studentId
     * @param studentDocumentId studentDocumentId
     * @return printTaskDto
     */
    PrintTaskDto addToCart(Long studentId, Long studentDocumentId);

}

package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.common.dto.pack.FileBean;
import com.suime.context.model.Document;
import com.suime.library.dto.DocumentDto;

/**
 * documentService
 * Created by ice 17/02/2016.
 */
public interface DocumentService extends GenericService<Document> {

    /**
     * 添加文档记录
     *
     * @param fileBean fileBean
     * @return mnsMessageDto
     */
    DocumentDto addDocument(FileBean fileBean);

}

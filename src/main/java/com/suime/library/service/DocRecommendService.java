package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.DocRecommend;
import com.suime.library.dto.StudentDocumentDto;

import java.util.List;

/**
 * docRecommendService
 * Created by ice 29/04/2016.
 */
public interface DocRecommendService extends GenericService<DocRecommend> {

    /**
     * 获取某个文档的相关推荐文档
     *
     * @param documentId 文档id
     * @param studentId  学生id
     * @param pageSize   获取条数
     * @return studentDocumentDto
     */
    List<StudentDocumentDto> fetchRecommendDocuments(Long documentId, Long studentId, int pageSize);

}

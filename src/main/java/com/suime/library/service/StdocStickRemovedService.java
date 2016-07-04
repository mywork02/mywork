package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.StdocStickRemoved;
import com.suime.library.dto.StudentDocumentDto;

import java.util.List;

/**
 * stdocStickRemovedService
 * Created by ice 14/03/2016.
 */
public interface StdocStickRemovedService extends GenericService<StdocStickRemoved> {

	/**
	 * 根据studentId 和 studentDocumentId 添加removed的记录
	 *
	 * @param studentId
	 * @param studentDocumentId
	 * @return stdocStickRemoved
	 */
	StdocStickRemoved addRemovedRecord(Long studentId, Long studentDocumentId);

    /**
     * 根据studentId 和 studentDocumentId 获取removed的记录
     *
     * @param studentId
     * @param studentDocumentId
     * @return
     */
    StdocStickRemoved findRemovedRecord(Long studentId, Long studentDocumentId);

    /**
     * 获取删除的推荐文档的 studentDocumentId
     *
     * @param studentId studentId
     * @return
     */
    List<Long> fetchRemovedStdocIds(Long studentId);

    /**
     * 获取推荐文档的,(以去除删除掉的不想看的推荐文档)
     *
     * @param studentId studentId
     * @return
     */
    List<StudentDocumentDto> fetchStickDocumentWithoutRemoved(Long studentId);

}

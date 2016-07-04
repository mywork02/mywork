package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.MarkRecord;
import com.suime.library.dto.MarkRecordDto;
import com.suime.library.dto.pack.MarkRecordBean;

/**
 * markRecordService
 * Created by ice 17/02/2016.
 */
public interface MarkRecordService extends GenericService<MarkRecord> {

	/**
	 * addMarkRecord
	 * @param markRecordBean markRecordBean
	 * @return markRecordDto
	 */
	MarkRecordDto addMarkRecord(MarkRecordBean markRecordBean);

	/**
	 * updateMarkRecord
	 * @param markRecordBean markRecordBean
	 * @return markRecordDto
	 */
	MarkRecordDto updateMarkRecord(MarkRecordBean markRecordBean);

	/**
	 * 文档收藏软删除
	 * 可收藏文集后的版本，不再建议根据文档id删除该记录
	 * @param markRecordBean markRecordBean
	 */
	@Deprecated
	void removeDocByItem(MarkRecordBean markRecordBean);

	/**
	 * 根据id软删除该记录
	 * @param markRecordId 收藏记录id
	 * @param studentId 当前登陆学生id
	 */
	void removeById(Long markRecordId, Long studentId);

	/**
	 * 分页获取用户收藏文档记录
	 *
	 * @param markRecordBean markRecordBean
	 * @param page           page
	 * @param pageSize       pageSize
	 * @return page
	 */
	Page pageMarkDocumentByItem(MarkRecordBean markRecordBean, int page, int pageSize);
	
	/**
	 * 分页获取收藏夹下面的内容，包含文档后缀
	 */
	Page fetchMarkStudentDocumentPage(MarkRecordBean markRecordBean ,int page, int pageSize);
}

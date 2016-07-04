package com.suime.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.sql.Conds;
import com.suime.context.model.MarkRecord;
import com.suime.context.model.RatingRecord;
import com.suime.context.model.StudentDocument;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.manager.MarkRecordManager;
import com.suime.library.manager.RatingRecordManager;
import com.suime.library.service.PermissionService;
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

	/**
	 * ratingRecordManager
	 */
	@Autowired
	@Qualifier("ratingRecordManager")
	private RatingRecordManager ratingRecordManager;

	/**
	 * markRecordManager
	 */
	@Autowired
	@Qualifier("markRecordManager")
	private MarkRecordManager markRecordManager;

	@Override
	public StudentDocumentDto updateAndGetInfo(Long studentId,StudentDocument studentDocument) {
		StudentDocumentDto dto = new StudentDocumentDto(studentDocument);
		Byte actived = 1;
		Conds ratingConds = new Conds();
		ratingConds.equal("student_id", studentId);
		ratingConds.equal("student_document_id",studentDocument.getId());
		ratingConds.equal("actived", actived);
		RatingRecord ratingRecord = ratingRecordManager.fetchSearchByConds(ratingConds);
		if (ratingRecord != null) {
				dto.setRatingRecordId(ratingRecord.getId());
				dto.setPersonalScore(ratingRecord.getScore());
		}
		MarkRecord markRecord = markRecordManager.fetchSearchByConds(ratingConds);
		if (markRecord != null) {
		dto.setMarkRecordId(markRecord.getId());
		}
		return dto;
	}
}

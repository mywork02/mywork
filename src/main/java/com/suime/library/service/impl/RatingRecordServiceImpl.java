package com.suime.library.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.RatingRecord;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.context.model.support.DocumentCountEnum;
import com.suime.library.dao.RatingRecordMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.RatingRecordDto;
import com.suime.library.dto.pack.RatingRecordBean;
import com.suime.library.service.RatingRecordService;

/**
 * ratingRecordService
 * Created by ice 17/02/2016.
 */
@Service("ratingRecordService")
public class RatingRecordServiceImpl extends GenericServiceImpl<RatingRecord> implements RatingRecordService {

	/**
	 * ratingRecordMapper
	 */
	@Autowired
	@Qualifier("ratingRecordMapper")
	private RatingRecordMapper ratingRecordMapper;

	/**
	 * studentMapper
	 */
	@Autowired
	@Qualifier("studentMapper")
	private StudentMapper studentMapper;

	/**
	 * studentDocumentMapper
	 */
	@Autowired
	@Qualifier("studentDocumentMapper")
	private StudentDocumentMapper studentDocumentMapper;

	@Override
	public GenericMapper<RatingRecord> getGenericMapper() {
		return ratingRecordMapper;
	}

	@Override
	public RatingRecordDto addRatingRecord(RatingRecordBean ratingRecordBean) {
		if (ratingRecordBean == null || ratingRecordBean.getStudentDocumentId() == null || ratingRecordBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		RatingRecord ratingRecord = this.fetchByItem(ratingRecordBean);
		if (ratingRecord != null) {
			return new RatingRecordDto(ratingRecord);
		}
		ratingRecord = ratingRecordBean.transToRatingRecord();
		Long studentDocumentId = ratingRecordBean.getStudentDocumentId();
		StudentDocument studentDocument = studentDocumentMapper.fetchById(studentDocumentId);
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().fileNotFound();
		}
		ratingRecord.setStudentDocumentId(studentDocumentId);

		Long studentId = ratingRecordBean.getStudentId();
		Student student = studentMapper.fetchById(studentId);
		if (student != null) {
			ratingRecord.setStudentId(studentId);
			ratingRecord.setStudentNickName(student.getNickName());// 学生昵称,暂时没有先占位
		}
		Timestamp createdAt = DateUtil.getSqlTimestamp();
		ratingRecord.setCreatedAt(createdAt);
		ratingRecord.setUpdatedAt(createdAt);
		ratingRecord.setScore(ratingRecordBean.getScore());
		Byte actived = 1;
		int rows = studentDocumentMapper.updateCountById(studentDocumentId, DocumentCountEnum.ratingCount.getText(), ratingRecord.getScore());
		if (rows != 1) {
			throw BusinessErrors.getInstance().paramsError();
		}
		ratingRecord.setActived(actived);
		this.save(ratingRecord);
		RatingRecordDto ratingRecordDto = new RatingRecordDto(ratingRecord);
		return ratingRecordDto;
	}

	/**
	 * 查询 ratingRecord
	 * @param ratingRecordBean
	 * @return ratingRecord
	 */
	private RatingRecord fetchByItem(RatingRecordBean ratingRecordBean) {
		if (ratingRecordBean == null) {
			return null;
		}
		Conds conds = new Conds();
		if (ratingRecordBean.getStudentDocumentId() != null) {
			conds.equal("t.student_document_id", ratingRecordBean.getStudentDocumentId());
		}
		if (ratingRecordBean.getStudentId() != null) {
			conds.equal("t.student_id", ratingRecordBean.getStudentId());
		}
		if (ratingRecordBean.getActived() != null) {
			conds.equal("t.actived", ratingRecordBean.getActived());
		} else {
			conds.equal("t.actived", 1);
		}
		return this.fetchSearchByConds(conds);
	}
}

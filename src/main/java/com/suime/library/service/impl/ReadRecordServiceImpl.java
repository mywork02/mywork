package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.ReadRecord;
import com.suime.context.model.Student;
import com.suime.context.model.support.DocumentCountEnum;
import com.suime.library.dao.ReadRecordMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.ReadRecordDto;
import com.suime.library.dto.pack.ReadRecordBean;
import com.suime.library.service.ReadRecordService;

/**
 * readRecordService
 * Created by ice 17/02/2016.
 */
@Service("readRecordService")
public class ReadRecordServiceImpl extends GenericServiceImpl<ReadRecord> implements ReadRecordService {

	/**
	 * readRecordMapper
	 */
	@Autowired
	@Qualifier("readRecordMapper")
	private ReadRecordMapper readRecordMapper;

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
	public GenericMapper<ReadRecord> getGenericMapper() {
		return readRecordMapper;
	}

	@Override
	public ReadRecordDto addReadRecord(ReadRecordBean readRecordBean) {
		if (readRecordBean == null || readRecordBean.getStudentDocumentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long studentDocumentId = readRecordBean.getStudentDocumentId();

		// 未登录状态
		int rows = studentDocumentMapper.updateCountById(studentDocumentId, DocumentCountEnum.readCount.getText(), 1);
		if (rows != 1) {
			throw BusinessErrors.getInstance().paramsError();
		}

		if (readRecordBean.getStudentId() == null) {
			return null;
		}

		ReadRecord readRecord = readRecordBean.transToReadRecord();
		readRecord.setStudentDocumentId(studentDocumentId);

		Long studentId = readRecordBean.getStudentId();
		Student student = studentMapper.fetchById(studentId);
		if (student != null) {
			readRecord.setStudentId(studentId);
			readRecord.setStudentNickName(student.getNickName());// 学生昵称,暂时没有先占位
		}
		Timestamp createdAt = DateUtil.getSqlTimestamp();
		readRecord.setCreatedAt(createdAt);
		readRecord.setUpdatedAt(createdAt);
		Byte actived = 1;
		readRecord.setActived(actived);
		this.save(readRecord);
		ReadRecordDto readRecordDto = new ReadRecordDto(readRecord);
		return readRecordDto;
	}

	@Override
	public Page pageByItem(ReadRecordBean readRecordBean, int page, int pageSize) {
		if (readRecordBean == null) {
			return null;
		}
		Conds conds = extractConds(readRecordBean);
		int count = this.count(conds);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		List<ReadRecordDto> list = this.readRecordMapper.fetchDtoSearchByPage(params);
		Page result = generatePage(page, pageSize, count, list);
		return result;
	}

	/**
	 * 封装查询条件
	 * @param readRecordBean
	 * @return conds
	 */
	private Conds extractConds(ReadRecordBean readRecordBean) {
		Conds conds = new Conds();
		if (readRecordBean.getStudentDocumentId() != null) {
			conds.equal("t.student_document_id", readRecordBean.getStudentDocumentId());
		}
		if (readRecordBean.getStudentId() != null) {
			conds.equal("t.student_id", readRecordBean.getStudentId());
		}
		if (readRecordBean.getActived() != null) {
			conds.equal("t.actived", readRecordBean.getActived());
		} else {
			conds.equal("t.actived", 1);
		}
		return conds;
	}

}

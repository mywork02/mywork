package com.suime.library.service.impl;

import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.File;
import com.suime.context.model.FileLink;
import com.suime.context.model.PrintRecord;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.context.model.support.DocumentCountEnum;
import com.suime.library.dao.FileLinkMapper;
import com.suime.library.dao.FileMapper;
import com.suime.library.dao.PrintRecordMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.PrintTaskDto;
import com.suime.library.dto.pack.FileLinkBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.PrintTaskMapper;
import com.suime.context.model.PrintTask;
import com.suime.library.service.PrintTaskService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * printTaskService
 * Created by ice 17/02/2016.
 */
@Service("printTaskService")
public class PrintTaskServiceImpl extends GenericServiceImpl<PrintTask> implements PrintTaskService {

	/**
	 * printTaskMapper
	 */
	@Autowired
	@Qualifier("printTaskMapper")
	private PrintTaskMapper printTaskMapper;

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

	/**
	 * fileLinkMapper
	 */
	@Autowired
	@Qualifier("fileLinkMapper")
	private FileLinkMapper fileLinkMapper;

	/**
	 * fileLinkMapper
	 */
	@Autowired
	@Qualifier("fileMapper")
	private FileMapper fileMapper;

	/**
	 * printRecordMapper
	 */
	@Autowired
	@Qualifier("printRecordMapper")
	private PrintRecordMapper printRecordMapper;

	@Override
	public GenericMapper<PrintTask> getGenericMapper() {
		return printTaskMapper;
	}

	@Override
	public int countInCart(Long studentId) {
		Conds conds = new Conds();
		conds.equal("t.student_id", studentId);
		conds.isNull("t.print_order_id");
		return this.count(conds);
	}

	@Override
	public PrintTaskDto addToCart(Long studentId, Long studentDocumentId) {
		Student student = this.studentMapper.fetchById(studentId);
		if (student == null) {
			throw BusinessErrors.getInstance().userNotExistsError();// 用户不存在
		}
		StudentDocument studentDocument = this.studentDocumentMapper.fetchById(studentDocumentId);
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().fileNotFound();// 文档未找到
		}
		if (studentDocument.getActived().intValue() == 0 && studentDocument.getStudentId() != studentId) {
			throw BusinessErrors.getInstance().fileNotFound();// 文档未找到
		}

		PrintTask printTask = new PrintTask();

		final Byte bZero = 0;
		Integer one = 1;
		Integer two = 2;
		Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
		FileLink fileLink = this.getFileLink(studentDocument);
		Long fileId = studentDocument.getFileId();
		File file = this.fileMapper.fetchById(fileId);
		printTask.setFilelinkId(fileLink.getId());
		printTask.setCreatedAt(sqlTimestamp);
		printTask.setFileKey(file.getKey());
		printTask.setCreatedAt(sqlTimestamp);
		printTask.setUpdatedAt(sqlTimestamp);
		printTask.setFileName(studentDocument.getFileName());
		printTask.setBothside(bZero);// 单面打印
		printTask.setColorful(bZero);// 非彩色打印
		printTask.setCopies(one);// 打印一份
		printTask.setHandouts("X11");// 一页一面
		printTask.setStartPage(-1);
		printTask.setCountPage(-1);// 打印页数,暂时未使用,值为-1
		printTask.setState(one);// 状态. 1:建立,2:打印中,3:打印完成
		printTask.setStudentId(student.getId());
		printTask.setLib(two.byteValue());// 任务来源,0:个人上传文件;1:楼长小文库;2:文库
		printTask.setPages(file.getPageCount());
		this.save(printTask);
		this.studentDocumentMapper.updateCountById(studentDocumentId, DocumentCountEnum.printCount.getText(), 1);

		PrintRecord printRecord = new PrintRecord();
		printRecord.setUpdatedAt(sqlTimestamp);
		printRecord.setActived(one.byteValue());
		printRecord.setPrintTaskId(printTask.getId());
		printRecord.setStudentNickName(student.getNickName());
		printRecord.setCreatedAt(sqlTimestamp);
		printRecord.setStudentId(studentId);
		printRecord.setStudentDocumentId(studentDocumentId);
		this.printRecordMapper.save(printRecord);

		return new PrintTaskDto(printTask);
	}

	/**
	 * 根据用户(学生) 和 文档,获取fileLink,如若没有则新建一条记录
	 * 之所以 新建fileLink 则是兼容之前的 nodejs 的代码
	 *
	 * @param studentDocument
	 * @return fileLink
	 */
	private FileLink getFileLink(StudentDocument studentDocument) {
		FileLinkBean fileLinkBean = new FileLinkBean();
		Long fileId = studentDocument.getFileId();
		Long studentId = studentDocument.getStudentId();
		fileLinkBean.setStudentId(studentId);
		fileLinkBean.setFileId(fileId);

		Conds conds = new Conds();
		conds.equal("t.file_id", fileId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("offset", 0);
		params.put("limit", 1);
		List<FileLink> fileLinks = this.fileLinkMapper.fetchSearchByPage(params);

		FileLink fileLink;
		if (fileLinks != null && !fileLinks.isEmpty()) {
			fileLink = fileLinks.get(0);
		} else {
			fileLink = new FileLink();
			fileLink.setStudentId(studentId);
			fileLink.setFileId(fileId);
			fileLink.setName(studentDocument.getFileName());
			Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
			fileLink.setCreatedAt(sqlTimestamp);
			fileLink.setUpdatedAt(sqlTimestamp);
			this.fileLinkMapper.save(fileLink);
		}
		return fileLink;
	}
}

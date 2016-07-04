package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.JsonUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.SendRecordType;
import com.suime.constants.SourceType;
import com.suime.context.model.Directory;
import com.suime.context.model.SendCache;
import com.suime.context.model.SendRecord;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.context.model.support.DirectoryDocument;
import com.suime.library.dao.DirectoryMapper;
import com.suime.library.dao.SendCacheMapper;
import com.suime.library.dao.SendRecordMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.SendRecordDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.SendRecordBean;
import com.suime.library.error.DirectoryErrors;
import com.suime.library.error.SendStdocErrors;
import com.suime.library.manager.SendRecordManager;
import com.suime.library.service.SendRecordService;

/**
 * sendRecordService
 * Created by chenqy 07/04/2016.
 */
@Service("sendRecordService")
public class SendRecordServiceImpl extends GenericServiceImpl<SendRecord> implements SendRecordService {

	/**
	 * sendRecordMapper
	 */
	@Autowired
	@Qualifier("sendRecordMapper")
	private SendRecordMapper sendRecordMapper;

	/**
	 * studentDocumentMapper
	 */
	@Autowired
	private StudentDocumentMapper studentDocumentMapper;

	/**
	 * studentMapper
	 */
	@Autowired
	private StudentMapper studentMapper;

	/**
	 * sendRecordManager
	 */
	@Autowired
	private SendRecordManager sendRecordManager;

	/**
	 * sendCacheMapper
	 */
	@Autowired
	private SendCacheMapper sendCacheMapper;

	/**
	 * directoryMapper
	 */
	@Autowired
	private DirectoryMapper directoryMapper;

	@Override
	public GenericMapper<SendRecord> getGenericMapper() {
		return sendRecordMapper;
	}

	@Override
	public void addDocument(SendRecordBean sendRecordBean) {
		if (sendRecordBean == null || sendRecordBean.getSenderId() == null || sendRecordBean.getReceiverId() == null || sendRecordBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		StudentDocument studentDocument = this.studentDocumentMapper.fetchById(sendRecordBean.getId());
		if (studentDocument == null || studentDocument.getActived() == 0) {
			throw BusinessErrors.getInstance().fileNotFound();
		}
		if (!studentDocument.getStudentId().equals(sendRecordBean.getSenderId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		Student receiver = this.studentMapper.fetchById(sendRecordBean.getReceiverId());
		if (receiver == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		SendRecord sendRecord = new SendRecord();
		SendCache sendCache = new SendCache();
		StudentDocument newStudentDocument = this.resetStdocObject(studentDocument, receiver);
		sendCache.setData(JsonUtil.toJsonString(newStudentDocument));
		this.sendCacheMapper.save(sendCache);
		Byte actived = 1;
		sendRecord.setActived(actived);
		sendRecord.setType(SendRecordType.DOCUMENT);
		sendRecord.setName(newStudentDocument.getName());
		sendRecord.setSendCacheId(sendCache.getId());
		sendRecord.setReceiverId(sendRecordBean.getReceiverId());
		sendRecord.setSenderId(sendRecordBean.getSenderId());
		this.save(sendRecord);
	}

	@Override
	public void addDirectory(SendRecordBean sendRecordBean) {
		if (sendRecordBean == null || sendRecordBean.getSenderId() == null || sendRecordBean.getReceiverId() == null || sendRecordBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Directory directory = this.directoryMapper.fetchById(sendRecordBean.getId());
		if (directory == null || directory.getActived() == null) {
			throw DirectoryErrors.getInstance().directoryNotExistsError();
		}
		if (!directory.getStudentId().equals(sendRecordBean.getSenderId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		Student receiver = this.studentMapper.fetchById(sendRecordBean.getReceiverId());
		if (receiver == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		SendRecord sendRecord = new SendRecord();
		SendCache sendCache = new SendCache();
		DirectoryDocument directoryDocument = this.getUnderDirectory(directory);
		sendCache.setData(JsonUtil.toJsonString(directoryDocument));
		this.sendCacheMapper.save(sendCache);
		Byte actived = 1;
		sendRecord.setActived(actived);
		sendRecord.setType(SendRecordType.DIRECTORY);
		sendRecord.setName(directory.getName());
		sendRecord.setSendCacheId(sendCache.getId());
		sendRecord.setReceiverId(sendRecordBean.getReceiverId());
		sendRecord.setSenderId(sendRecordBean.getSenderId());
		this.save(sendRecord);
	}

	/**
	 * 获取目录下的所有东西,并转换成中间存储结构
	 * @param directory
	 * @return DirectoryDocument
	 */
	private DirectoryDocument getUnderDirectory(Directory directory) {
		DirectoryDocument dirDocument = new DirectoryDocument();
		Directory newDir = new Directory();
		List<StudentDocument> documents;
		BeanUtils.copyProperties(directory, newDir);
		dirDocument.setDirectory(newDir);

		Conds conds = new Conds();
		conds.equal("directory_id", directory.getId());
		conds.equal("actived", 1);
		Map<String, Object> params = new HashMap<>();
		params.put("conds", conds);
		documents = this.studentDocumentMapper.fetchSearchByPage(params);
		dirDocument.setDocuments(documents);

		conds = new Conds();
		conds.equal("parent_id", directory.getId());
		conds.equal("actived", 1);
		params = new HashMap<>();
		params.put("conds", conds);
		List<Directory> subDirectorys = this.directoryMapper.fetchSearchByPage(params);
		List<DirectoryDocument> subs = new ArrayList<>();
		for (Directory sub : subDirectorys) {
			subs.add(getUnderDirectory(sub));
		}
		dirDocument.setSubDirectorys(subs);
		return dirDocument;
	}

	/**
	 * 重置发送文档信息
	 * @param target
	 * @return StudentDocument
	 */
	private StudentDocument resetStdocObject(StudentDocument target, Student receiver) {

		StudentDocument studentDocument = new StudentDocument();
		BeanUtils.copyProperties(target, studentDocument);
		Integer zero = 0;
		Timestamp currentTime = DateUtil.getSqlTimestamp();
		// studentDocument.setId(null);
		studentDocument.setCreatedAt(currentTime);
		studentDocument.setUpdatedAt(currentTime);
		studentDocument.setStudentId(receiver.getId());
		studentDocument.setStudentNickName(receiver.getNickName());
		studentDocument.setCommentCount(zero);
		studentDocument.setMarkCount(zero);
		studentDocument.setPrintCount(zero);
		studentDocument.setRatingCount(zero);
		studentDocument.setReadCount(zero);
		studentDocument.setShareCount(zero);
		studentDocument.setSource(SourceType.RECEIVE_DOCUMENT);
		studentDocument.setDirectoryId(null);
		studentDocument.setStick((byte) 0);// 非置顶
		studentDocument.setType((byte) 1);// 默认普通文档
		studentDocument.setRecommended((byte) 0);// 默认不被推荐
		return studentDocument;
	}

	@Override
	public void createReceive(Long id, Long receiverId) {

		SendRecord sendRecord = this.fetchById(id);
		if (!sendRecord.getReceiverId().equals(receiverId)) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		if (sendRecord.getIsAccepted() != null) {
			throw SendStdocErrors.getInstance().alreadyProcessedError();
		}
		SendCache sendCache = this.sendCacheMapper.fetchById(sendRecord.getSendCacheId());

		Byte accepted = 1;
		Timestamp currentTime = DateUtil.getSqlTimestamp();
		if (sendRecord.getType().equals(SendRecordType.DOCUMENT)) {
			StudentDocument studentDocument = JsonUtil.parseObject(sendCache.getData(), StudentDocument.class);
			this.studentDocumentMapper.save(studentDocument);
			sendRecord.setIsAccepted(accepted);
			sendRecord.setUpdatedAt(currentTime);
			sendRecord.setAssociatedId(studentDocument.getId());
		} else {
			DirectoryDocument directoryDocument = JsonUtil.parseObject(sendCache.getData(), DirectoryDocument.class);
			Student receiver = this.studentMapper.fetchById(receiverId);
			Long dirId = saveReceiveDirectory(directoryDocument, receiver, null);
			sendRecord.setIsAccepted(accepted);
			sendRecord.setUpdatedAt(currentTime);
			sendRecord.setAssociatedId(dirId);
		}
		this.sendCacheMapper.delete(sendCache.getId());
		this.update(sendRecord);
	}

	/**
	 * saveReceiveDirectory
	 * @param directoryDocument
	 * @param receiver
	 * @param parentId
	 * @return directory id
	 */
	private Long saveReceiveDirectory(DirectoryDocument directoryDocument, Student receiver, Long parentId) {
		Directory directory = directoryDocument.getDirectory();
		directory.setSource(SourceType.RECEIVE_DIRECTORY);
		directory.setStudentId(receiver.getId());
		directory.setParentId(parentId);
		this.directoryMapper.save(directory);

		Long directoryId = directory.getId();

		List<StudentDocument> documents = directoryDocument.getDocuments();
		for (StudentDocument sd : documents) {
			StudentDocument newSd = this.resetStdocObject(sd, receiver);
			newSd.setDirectoryId(directoryId);
			this.studentDocumentMapper.save(newSd);
		}

		List<DirectoryDocument> subDirectorys = directoryDocument.getSubDirectorys();
		for (DirectoryDocument sub : subDirectorys) {
			saveReceiveDirectory(sub, receiver, directoryId);
		}
		return directoryId;
	}

	@Override
	public void createRefuse(Long id, Long receiverId) {
		SendRecord sendRecord = this.fetchById(id);
		if (!sendRecord.getReceiverId().equals(receiverId)) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		if (sendRecord.getIsAccepted() != null) {
			throw SendStdocErrors.getInstance().alreadyProcessedError();
		}
		Timestamp currentTime = DateUtil.getSqlTimestamp();
		Byte refused = 0;
		this.sendCacheMapper.delete(sendRecord.getSendCacheId());
		sendRecord.setUpdatedAt(currentTime);
		sendRecord.setIsAccepted(refused);
		this.update(sendRecord);
	}

	@Override
	public Page pageReceivedDocument(Long receiverId, int page, int pageSize) {
		Conds conds = new Conds();
		Byte actived = 1;
		//Byte accepted = 1;
		Byte source = 2;
/*		conds.equal("send.receiver_id", receiverId);
		conds.equal("send.is_accepted", accepted);
		conds.equal("send.type", SendRecordType.DOCUMENT);
*/		conds.equal("sd.actived", actived);
		conds.equal("sd.source", source);
		conds.notNull("sd.sender_id");
		conds.equal("sd.student_id", receiverId);
		int count = this.sendRecordManager.countReceivedDocument(conds);
		Map<String, Object> params = this.generateParamsMap(conds, null, page, pageSize);
		List<StudentDocumentDto> list = this.sendRecordMapper.receivedDocumentDto(params);
		return this.generatePage(page, pageSize, count, list);
	}

	@Override
	public Page fetchPageDto(Long receiverId, Byte isAccepted, int page, int pageSize) {
		Conds conds = new Conds();
		Conds countConds = new Conds();
		conds.equal("send.actived", (byte) 1);
		countConds.equal("actived", (byte) 1);
		if (isAccepted == null) {
			conds.isNull("send.is_accepted");
			countConds.isNull("is_accepted");
		} else {
			conds.equal("send.is_accepted", isAccepted);
			countConds.equal("is_accepted", isAccepted);
		}
		conds.equal("send.receiver_id", receiverId);
		countConds.equal("receiver_id", receiverId);
		int count = this.count(countConds);
		Map<String, Object> params = this.generateParamsMap(conds, null, page, pageSize);
		List<SendRecordDto> list = this.sendRecordMapper.fetchPageDto(params);
		return this.generatePage(page, pageSize, count, list);
	}
}

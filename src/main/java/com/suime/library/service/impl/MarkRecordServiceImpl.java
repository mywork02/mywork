package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.ioc.SpringContext;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.SuimeLibraryConstants;
import com.suime.context.model.CollectedWorks;
import com.suime.context.model.MarkRecord;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.context.model.support.DocumentCountEnum;
import com.suime.context.model.support.MessageExtraKeyEnum;
import com.suime.context.model.support.MessageTypeConstants;
import com.suime.context.model.support.MessageTypeEnum;
import com.suime.library.dao.FavoriteMapper;
import com.suime.library.dao.MarkRecordMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.MarkRecordDto;
import com.suime.library.dto.PointDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.MarkRecordBean;
import com.suime.library.dto.pack.MessageBean;
import com.suime.library.error.LibraryErrors;
import com.suime.library.error.MarkRecordErrors;
import com.suime.library.manager.CollectedWorksManager;
import com.suime.library.manager.MessageManager;
import com.suime.library.service.MarkRecordService;

import me.sui.user.remote.service.StudentPointRemoteService;

/**
 * markRecordService
 * Created by ice 17/02/2016.
 */
@Service("markRecordService")
public class MarkRecordServiceImpl extends GenericServiceImpl<MarkRecord> implements MarkRecordService {

	/**
	 * markRecordMapper
	 */
	@Autowired
	@Qualifier("markRecordMapper")
	private MarkRecordMapper markRecordMapper;

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
	 * messageManager
	 */
	@Autowired
	@Qualifier("messageManager")
	private MessageManager messageManager;

	/**
	 * studentPointRemoteService
	 */
	@Autowired
	@Qualifier("studentPointRemoteService")
	private StudentPointRemoteService studentPointRemoteService;

	/**
	 * collectedWorksManager
	 */
	@Autowired
	@Qualifier("collectedWorksManager")
	private CollectedWorksManager collectedWorksManager;

	/**
	 * favoriteMapper
	 */
	@Autowired
	@Qualifier("favoriteMapper")
	private FavoriteMapper favoriteMapper;

	@Override
	public GenericMapper<MarkRecord> getGenericMapper() {
		return markRecordMapper;
	}

	@Override
	public MarkRecordDto addMarkRecord(MarkRecordBean markRecordBean) {
		if (markRecordBean == null || markRecordBean.getStudentId() == null || markRecordBean.getType() == null || markRecordBean.getAssociatedId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long ownerId = null;
		String alias = null;
		String associatedName = null;
		Long studentId = markRecordBean.getStudentId();
		Student student = studentMapper.fetchById(studentId);
		if (student == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}

		Long associatedId = markRecordBean.getAssociatedId();
		if (SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT.equals(markRecordBean.getType())) {
			// add文档收藏
			StudentDocument studentDocument = checkStudentDocument(markRecordBean, associatedId);
			ownerId = studentDocument.getStudentId();
			associatedName = studentDocument.getName();
			alias = associatedName;
		} else {
			CollectedWorks collectedWorks = checkCollectedWords(markRecordBean, associatedId);
			ownerId = collectedWorks.getStudentId();
			associatedName = collectedWorks.getName();
			alias = associatedName;
		}
		if (StringUtils.isNotBlank(markRecordBean.getAlias())) {
			alias = markRecordBean.getAlias();
		}
		MarkRecordDto markRecordDto = addOrUpdateRecord(student, markRecordBean, ownerId, associatedName, alias);
		return markRecordDto;
	}

	/**
	 * add or update mark record
	 * @param student
	 * @param markRecordBean
	 * @param ownerId
	 * @param associatedName
	 * @param alias
	 * @return markRecordDto
	 */
	private MarkRecordDto addOrUpdateRecord(Student student, MarkRecordBean markRecordBean, Long ownerId, String associatedName, String alias) {
		MarkRecord markRecord = this.fetchByItem(markRecordBean);
		boolean flagMarked = false;// 之前是否收藏过，如果已经收藏过了，则不在给用户发送消息推送
		Long favoriteId = markRecordBean.getFavoriteId();

		Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
		if (markRecord != null) {
			flagMarked = true;
		} else {
			markRecord = markRecordBean.transToMarkRecord();
			markRecord.setAssociatedId(markRecordBean.getAssociatedId());
			if (student != null) {
				markRecord.setStudentId(student.getId());
				markRecord.setStudentNickName(student.getNickName());
			} else {
				throw BusinessErrors.getInstance().userNotExistsError();
			}
		}
		if (favoriteId != null) {
			markRecord.setFavoriteId(favoriteId);
		}
		if (StringUtils.isNotBlank(alias)) {
			markRecord.setAlias(alias);
		}
		markRecord.setActived(SuimeLibraryConstants.COMMON_ACTIVED_VALID);
		markRecord.setUpdatedAt(sqlTimestamp);

		MarkRecordDto markRecordDto = new MarkRecordDto(markRecord);
		if (!flagMarked) {
			markRecord.setCreatedAt(sqlTimestamp);
			this.save(markRecord);
			int rows = studentDocumentMapper.updateCountById(markRecordBean.getStudentDocumentId(), DocumentCountEnum.markCount.getText(), 1);
			if (rows != 1) {
				throw BusinessErrors.getInstance().paramsError();
			}
			this.addMarkRecordMessage(markRecord, ownerId, associatedName);
			PointDto pointDto = addStudentPointLog(markRecord, student);
			markRecordDto.setPoint(pointDto);
		} else {
			this.update(markRecord);
		}
		return markRecordDto;
	}

	@Override
	public MarkRecordDto updateMarkRecord(MarkRecordBean markRecordBean) {
		if (markRecordBean == null || markRecordBean.getStudentId() == null || markRecordBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		MarkRecord markRecord = this.fetchById(markRecordBean.getId());
		if (markRecord == null || SuimeLibraryConstants.COMMON_ACTIVED_INVALID.equals(markRecord.getActived())) {
			throw MarkRecordErrors.getInstance().markRecordNotFoundError();
		}
		Student student = studentMapper.fetchById(markRecordBean.getStudentId());
		if (student == null) {
			throw BusinessErrors.getInstance().userNotExistsError();
		}
		if (markRecordBean.getStudentId().equals(markRecord.getStudentId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		markRecord.setStudentId(student.getId());
		markRecord.setStudentNickName(student.getNickName());
		Long favoriteId = markRecordBean.getFavoriteId();
		if (favoriteId != null) {
			markRecord.setFavoriteId(favoriteId);
		}
		if (StringUtils.isNotBlank(markRecord.getAlias())) {
			markRecord.setAlias(markRecordBean.getAlias());
		}
		Timestamp sqlTimestamp = DateUtil.getSqlTimestamp();
		markRecord.setActived(SuimeLibraryConstants.COMMON_ACTIVED_VALID);
		markRecord.setUpdatedAt(sqlTimestamp);
		return null;
	}

	/**
	 * 添加积分
	 *
	 * @param markRecord
	 * @param student
	 * @return point dto
	 */
	private PointDto addStudentPointLog(MarkRecord markRecord, Student student) {
		PointDto pointDto = new PointDto();
		return pointDto;
	}

	@Override
	public void removeDocByItem(MarkRecordBean markRecordBean) {
		if (markRecordBean == null || markRecordBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		if (markRecordBean.getAssociatedId() == null && markRecordBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		if (markRecordBean.getAssociatedId() != null && markRecordBean.getType() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		MarkRecord markRecord = this.fetchByItem(markRecordBean);
		if (markRecord == null) {
			throw MarkRecordErrors.getInstance().markRecordNotFoundError();// 没有该记录
		}

		if (SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT.equals(markRecordBean.getType())) {
			StudentDocument studentDocument = studentDocumentMapper.fetchById(markRecordBean.getStudentDocumentId());
			if (studentDocument == null) {
				throw BusinessErrors.getInstance().fileNotFound();
			}
			if (SuimeLibraryConstants.STUDENT_DOCUMENT_STICK.equals(studentDocument.getStick())) {
				return;
			}
		}

		if (SuimeLibraryConstants.COMMON_ACTIVED_INVALID.equals(markRecord.getActived())) {
			throw MarkRecordErrors.getInstance().alreadyRemovedError();// 已删除
		}
		if (SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT.equals(markRecord.getType())) {
			int rows = studentDocumentMapper.updateCountById(markRecord.getAssociatedId(), DocumentCountEnum.markCount.getText(), -1);
			if (rows != 1) {
				throw BusinessErrors.getInstance().paramsError();
			}
		}
		markRecord.setActived(SuimeLibraryConstants.COMMON_ACTIVED_INVALID);
		markRecord.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.update(markRecord);
	}

	@Override
	public void removeById(Long markRecordId, Long studentId) {
		if (markRecordId == null || studentId == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		MarkRecord markRecord = this.fetchById(markRecordId);
		if (markRecord == null) {
			throw MarkRecordErrors.getInstance().markRecordNotFoundError();
		}
		if (SuimeLibraryConstants.COMMON_ACTIVED_INVALID.equals(markRecord.getActived())) {
			return;
		}
		if (!studentId.equals(markRecord.getId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		if (SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT.equals(markRecord.getType())) {
			int rows = studentDocumentMapper.updateCountById(markRecord.getAssociatedId(), DocumentCountEnum.markCount.getText(), -1);
			if (rows != 1) {
				throw BusinessErrors.getInstance().paramsError();
			}
		}
		markRecord.setActived(SuimeLibraryConstants.COMMON_ACTIVED_INVALID);
		markRecord.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.update(markRecord);
	}

	/**
	 * 根据条件查询 markRecord
	 * @param markRecordBean
	 * @return markRecord
	 */
	private MarkRecord fetchByItem(MarkRecordBean markRecordBean) {
		if (markRecordBean == null) {
			return null;
		}
		Conds conds = new Conds();
		if (markRecordBean.getActived() != null) {
			conds.equal("t.actived", markRecordBean.getActived());
		}
		// if (markRecordBean.getStudentDocumentId() != null) {
		// conds.equal("t.student_document_id", markRecordBean.getStudentDocumentId());
		// }
		if (markRecordBean.getStudentId() != null) {
			conds.equal("t.student_id", markRecordBean.getStudentId());
		}
		if (markRecordBean.getType() != null) {
			conds.equal("t.type", markRecordBean.getType());
		}
		if (markRecordBean.getAssociatedId() != null) {
			conds.equal("t.associated_id", markRecordBean.getAssociatedId());
		}
		if (markRecordBean.getId()!=null){
			conds.equal("t.id", markRecordBean.getId());
		}
		return this.fetchSearchByConds(conds);
	}

	@Override
	public Page pageMarkDocumentByItem(MarkRecordBean markRecordBean, int page, int pageSize) {
		if (markRecordBean == null) {
			return null;
		}
		Conds conds = new Conds();
		Conds countConds = new Conds();
		if (markRecordBean.getStudentId() != null) {
			conds.equal("mr.student_id", markRecordBean.getStudentId());
			countConds.equal("student_id", markRecordBean.getStudentId());
		}
		if (markRecordBean.getActived() != null) {
			conds.equal("mr.actived", markRecordBean.getActived());
			countConds.equal("actived", markRecordBean.getActived());
		}
		Sort sort = null;
		int count = this.count(countConds);
		Map<String, Object> paramsMap = this.generateParamsMap(conds, sort, page, pageSize);
		List<StudentDocumentDto> list = this.markRecordMapper.fetchMarkedStudentDocument(paramsMap);

		return generatePage(page, pageSize, count, list);
	}

	/**
	 * 添加收藏消息记录
	 *
	 * @param markRecord 收藏记录
	 * @param receiverId 接收人id
	 * @param name       文档 或 目录 名称
	 */
	private void addMarkRecordMessage(MarkRecord markRecord, Long receiverId, String name) {
		MessageBean messageBean = new MessageBean();
		messageBean.setStudentDocumentId(markRecord.getStudentDocumentId());
		messageBean.setStudentDocumentName(name);
		messageBean.setReceiverId(receiverId);
		messageBean.setRelateId(markRecord.getId());
		messageBean.setSenderId(markRecord.getStudentId());
		messageBean.setType(MessageTypeEnum.preview);
		if (markRecord.getType() == null || SuimeLibraryConstants.MARK_RECORD_TYPE_DOCUMENT.equals(markRecord.getType())) {
			messageBean.setMessageType(MessageTypeConstants.MARK);
			messageBean.setContent(SpringContext.getText("message.mark", name));
		} else {
			messageBean.setMessageType(MessageTypeConstants.MARK_COLLECTED_WORKS);
			messageBean.setContent(SpringContext.getText("message.mark_collected_works", name));
		}
		messageBean.setExtraKey(MessageExtraKeyEnum.wenku);
		messageManager.add(messageBean);
	}

	/**
	 * 验证待收藏的文集是否合理
	 * @param markRecordBean
	 * @param associatedId
	 * @return 待收藏文集
	 */
	private CollectedWorks checkCollectedWords(MarkRecordBean markRecordBean, Long associatedId) {
		CollectedWorks collectedWorks = collectedWorksManager.fetchById(associatedId);
		if (collectedWorks == null || SuimeLibraryConstants.COMMON_ACTIVED_INVALID.equals(collectedWorks.getActived())) {
			throw LibraryErrors.getInstance().collectedWorksNotFound();
		}
		if (markRecordBean.getStudentId().equals(collectedWorks.getStudentId())) {
			throw LibraryErrors.getInstance().canNotMarkOwnCollectedWorks();
		}
		return collectedWorks;
	}

	/**
	 * 验证待收藏文档是否合理
	 * @param markRecordBean
	 * @param associatedId
	 * @return 待收藏文档
	 */
	private StudentDocument checkStudentDocument(MarkRecordBean markRecordBean, Long associatedId) {
		StudentDocument studentDocument = studentDocumentMapper.fetchById(associatedId);
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().fileNotFound();
		}
		if (SuimeLibraryConstants.STUDENT_DOCUMENT_STICK.equals(studentDocument.getStick())) {
			return null;
		}
		if (studentDocument.getStudentId().equals(markRecordBean.getStudentId())) {
			throw MarkRecordErrors.getInstance().canNotOwnFileError();
		}
		return studentDocument;
	}

	@Override
	public Page fetchMarkStudentDocumentPage(MarkRecordBean markRecordBean, int page, int pageSize) {
		Conds conds = new Conds();
		Sort sort =new Sort("m.type", OrderType.DESC);
		if(markRecordBean.getFavoriteId()!=null){
			conds.equal("m.favorite_id",markRecordBean.getFavoriteId());
		}
		if(markRecordBean.getStudentId()!=null){
			conds.equal("m.student_id", markRecordBean.getStudentId());
		}
		Byte actived = 1;
		conds.equal("m.actived", actived);
		Map<String, Object> paramsMap = this.generateParamsMap(conds, sort, page, pageSize);
		int count = markRecordMapper.fetchMarkStudentDocumentPageCount(paramsMap);
		List<MarkRecordDto> list = this.markRecordMapper.fetchMarkStudentDocumentPage(paramsMap);
		return generatePage(page, pageSize, count, list);
	}
}

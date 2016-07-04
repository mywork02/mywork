package com.suime.library.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Constants;
import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.framework.utils.JsonUtil;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.constants.PermissionType;
import com.suime.constants.SuimeLibraryConstants;
import com.suime.context.model.Category;
import com.suime.context.model.Directory;
import com.suime.context.model.DirectoryContentRels;
import com.suime.context.model.Document;
import com.suime.context.model.File;
import com.suime.context.model.MajorGroup;
import com.suime.context.model.MarkRecord;
import com.suime.context.model.OperationRecord;
import com.suime.context.model.RatingRecord;
import com.suime.context.model.School;
import com.suime.context.model.Student;
import com.suime.context.model.StudentDocument;
import com.suime.context.model.Tags;
import com.suime.library.dao.CategoryMapper;
import com.suime.library.dao.DirectoryContentRelsMapper;
import com.suime.library.dao.DirectoryMapper;
import com.suime.library.dao.DocumentMapper;
import com.suime.library.dao.FileMapper;
import com.suime.library.dao.MajorGroupMapper;
import com.suime.library.dao.OperationRecordMapper;
import com.suime.library.dao.SchoolMapper;
import com.suime.library.dao.StudentDocumentMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.DocPermissionBean;
import com.suime.library.dto.pack.DocumentMoveBean;
import com.suime.library.dto.pack.SearchAdminBean;
import com.suime.library.dto.pack.SearchBean;
import com.suime.library.dto.pack.StudentDocumentBean;
import com.suime.library.error.DirectoryErrors;
import com.suime.library.manager.MarkRecordManager;
import com.suime.library.manager.RatingRecordManager;
import com.suime.library.manager.StudentDocumentManager;
import com.suime.library.manager.TagsManager;
import com.suime.library.service.StudentDocumentService;
import com.suime.library.service.support.OrderFieldEnum;

import me.sui.user.remote.service.StudentPointRemoteService;

/**
 * studentDocumentService
 * Created by ice 17/02/2016.
 */
@Service("studentDocumentService")
public class StudentDocumentServiceImpl extends GenericServiceImpl<StudentDocument> implements StudentDocumentService {

	/**
	 * studentDocumentMapper
	 */
	@Autowired
	@Qualifier("studentDocumentMapper")
	private StudentDocumentMapper studentDocumentMapper;

	/**
	 * studentDocumentManager
	 */
	@Autowired
	@Qualifier("studentDocumentManager")
	private StudentDocumentManager studentDocumentManager;

	/**
	 * documentMapper
	 */
	@Autowired
	@Qualifier("documentMapper")
	private DocumentMapper documentMapper;

	/**
	 * studentMapper
	 */
	@Autowired
	@Qualifier("studentMapper")
	private StudentMapper studentMapper;

	/**
	 * schoolMapper
	 */
	@Autowired
	@Qualifier("schoolMapper")
	private SchoolMapper schoolMapper;

	/**
	 * categoryMapper
	 */
	@Autowired
	@Qualifier("categoryMapper")
	private CategoryMapper categoryMapper;

	/**
	 * fileMapper
	 */
	@Autowired
	@Qualifier("fileMapper")
	private FileMapper fileMapper;

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

	/**
	 * majorGroupMapper
	 */
	@Autowired
	@Qualifier("majorGroupMapper")
	private MajorGroupMapper majorGroupMapper;

	/**
	 * directoryMapper
	 */
	@Autowired
	@Qualifier("directoryMapper")
	private DirectoryMapper directoryMapper;

	/**
	 * tagsManager
	 */
	@Autowired
	@Qualifier("tagsManager")
	private TagsManager tagsManager;

	/**
	 * studentPointRemoteService
	 */
	@Autowired
	@Qualifier("studentPointRemoteService")
	private StudentPointRemoteService studentPointRemoteService;

	@Autowired
	private OperationRecordMapper operationRecordMapper;

	@Autowired
	private DirectoryContentRelsMapper directoryContentRelsMapper;

	@Override
	public GenericMapper<StudentDocument> getGenericMapper() {
		return studentDocumentMapper;
	}

	@Override
	public Page pageByItem(SearchBean searchBean, int curPage, int pageSize) {
		Long studentId = searchBean.getStudentId();
		Map<String, Object> params = extractSchool(studentId);
		Conds conds = new Conds();
		Conds beanConds = this.generateWhereSql(searchBean);
		if (beanConds != null) {
			conds.getConds().addAll(beanConds.getConds());
		}

		params.put("conds", conds);
		params.put("offset", curPage > 0 ? ((curPage - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		if (searchBean.getIds() != null && !searchBean.getIds().isEmpty()) {
			params.put("ids", searchBean.getIds());
		} else if (StringUtils.isNotBlank(searchBean.getText())) {
			params.put("searchText", searchBean.getText());
		}
		String sortField = searchBean.getSortField();
		if (searchBean.getSort() == null) {
			searchBean.setSort(OrderType.DESC);
		}
		if (StringUtils.isBlank(sortField)) {
			sortField = OrderFieldEnum.READ_COUNT.name();
		}
		Sort sort = new Sort(sortField, searchBean.getSort());
		params.put("sort", sort);

		int totalCount = this.studentDocumentMapper.countSearch(params);

		List<StudentDocumentDto> list = this.studentDocumentMapper.fetchDtoSearch(params);
		return generatePage(curPage, pageSize, totalCount, list);
	}

	private Map<String, Object> extractSchool(Long studentId) {
		Long schoolId = 0L;
		Long cityId = 0L;
		Map<String, Object> params = new HashMap<String, Object>();
		if (studentId != null) {
			Student student = studentMapper.fetchById(studentId);
			if (student == null) {
				throw BusinessErrors.getInstance().userNotExistsError();
			}
			params.put("studentId", student.getId());
			School school = schoolMapper.fetchById(student.getSchoolId());
			if (school != null) {
				schoolId = school.getId();
				params.put("schoolId", schoolId);
				if (school.getCityId() != null) {
					cityId = school.getCityId();
					params.put("cityId", cityId);
				}
			}
		}
		return params;
	}

	private Conds generateWhereSql(SearchBean searchBean) {
		if (searchBean == null) {
			return null;
		}
		Byte actived = 1;
		Conds conds = new Conds();

		conds.equal("sd.review_state", 2);
		conds.equal("sd.actived", actived);
		conds.equal("sd.permission", 1);
		conds.equal("file.has_Thumbnail", 1);
		conds.equal("file.state", 3);
		conds.notNull("file.page_Count");

		/*if (StringUtils.isNotBlank(searchBean.getText())) {
		    conds.like("sd.name", searchBean.getText());
		
		//            sqlBuffer.append(" and (sd.name like ?  or sd.tags like ? or document.content like ?)");
		//            String searchText = Constants.KEY_PERCENT + searchBean.getText() + Constants.KEY_PERCENT;
		//            values.add(searchText);
		//            String tagsText = Constants.KEY_PERCENT + searchBean.getText() + Constants.KEY_PERCENT;
		//            values.add(tagsText);
		//            String contentText = Constants.KEY_PERCENT + searchBean.getText() + Constants.KEY_PERCENT;
		//            values.add(contentText);
		}*/

		if (searchBean.getTagId() != null) {
			Tags tags = tagsManager.fetchById(searchBean.getTagId());
			if (tags != null && actived.equals(tags.getActived())) {
				conds.like("sd.tags", tags.getText());
				// Byte tagsType = tags.getType();
				// if (tagsType.equals(Byte.valueOf("1"))) {
				//
				// sqlBuffer.append(" and sd.tags like ? ");
				// String tagsText = Constants.KEY_PERCENT + Constants.VALUE_SIMPLE_SPLIT_CHAR + tags.getText() + Constants.VALUE_SIMPLE_SPLIT_CHAR
				// + Constants.KEY_PERCENT;
				// values.add(tagsText);
				// } else if (tagsType.equals(Byte.valueOf("9"))) {
				// Category category = tags.getCategory();
				// if (category != null) {
				// sqlBuffer.append(" and sd.category_code like ? ");
				// values.add(category.getCode() + Constants.KEY_PERCENT);
				// }
				// }
			}
		}
		if (searchBean.getSchoolId() != null) {
			conds.equal("sd.school_id", searchBean.getSchoolId());
		}
		if (searchBean.getCategoryId() != null) {
			Category category = categoryMapper.fetchById(searchBean.getCategoryId());
			if (category != null && category.getActived().equals(actived)) {
				conds.rightLike("sd.category_code", category.getCode());
			}
		} else {
			if (StringUtils.isNotBlank(searchBean.getMajorGroupCode())) {
				conds.rightLike("sd.category_code", "1" + searchBean.getMajorGroupCode());
			}
		}
		return conds;
	}

	@Override
	public List<StudentDocumentDto> limitListByItem(SearchBean searchBean, int pageSize) {
		Long studentId = searchBean.getStudentId();
		Map<String, Object> params = extractSchool(studentId);

		Byte actived = 1;
		Conds conds = new Conds();
		conds.equal("sd.review_State", 2);
		conds.equal("sd.actived", actived);
		conds.equal("file.has_Thumbnail", 1);
		conds.equal("file.state", 3);
		conds.notNull("file.page_Count");

		if (searchBean.getCategoryId() != null) {
			Category category = categoryMapper.fetchById(searchBean.getCategoryId());
			if (category != null && category.getActived().equals(actived)) {
				conds.rightLike("sd.category_code", category.getCode());
			}
		}
		if (searchBean.getDirectoryId() != null) {
			conds.equal("sd.directory_id", searchBean.getDirectoryId());
		}
		if (searchBean.getPermission() != null) {
			conds.equal("sd.permission", searchBean.getPermission());
		}
		if (StringUtils.isNotBlank(searchBean.getKeyWord())) {
			String keyWord;
			if (searchBean.getIsSplit()) {
				keyWord = Constants.VALUE_SIMPLE_SPLIT_CHAR + searchBean.getKeyWord() + Constants.VALUE_SIMPLE_SPLIT_CHAR;
			} else {
				keyWord = searchBean.getKeyWord();
			}
			conds.like("sd.tags", keyWord);
		}
		String sortField = searchBean.getSortField();
		if (searchBean.getSort() == null) {
			searchBean.setSort(OrderType.DESC);
		}
		if (StringUtils.isBlank(sortField)) {
			sortField = OrderFieldEnum.READ_COUNT.name();
		}
		Sort sort = new Sort(sortField, searchBean.getSort());

		params.put("conds", conds);
		params.put("offset", 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		params.put("sort", sort);

		return this.studentDocumentMapper.fetchDtoSearch(params);
	}

	@Override
	public StudentDocumentDto updateAndGetInfo(Long id, Long studentId, Boolean isAdmin) {
		StudentDocument studentDocument = this.fetchById(id);
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		if (!isAdmin) {
			boolean flagSelfDocument = false;
			if (studentId != null) {
				flagSelfDocument = studentDocument.getStudentId().longValue() == studentId.longValue();
			}
			if (studentDocument == null || studentDocument.getActived().intValue() != 1) {
				throw BusinessErrors.getInstance().fileNotFound();
			}
			if (studentDocument.getPermission().intValue() == 3 && !flagSelfDocument) {
				throw BusinessErrors.getInstance().fileNoAuth();
			}
			if (studentDocument.getReviewState().intValue() != 2 && !flagSelfDocument) {
				throw BusinessErrors.getInstance().fileNoAuth();
			}
		}
		File file = fileMapper.fetchById(studentDocument.getFileId());
		if (!file.getPageCount().equals(studentDocument.getPageCount())) {
			studentDocument.setPageCount(file.getPageCount());
			this.update(studentDocument);
		}
		StudentDocumentDto dto = new StudentDocumentDto(studentDocument);

		if (studentId != null) {
			Byte actived = 1;
			Conds conds = new Conds();
			conds.equal("student_id", studentId);
			conds.equal("student_document_id", id);
			conds.equal("actived", actived);

			RatingRecord ratingRecord = ratingRecordManager.fetchSearchByConds(conds);
			if (ratingRecord != null) {
				dto.setRatingRecordId(ratingRecord.getId());
				dto.setPersonalScore(ratingRecord.getScore());
			}
			MarkRecord markRecord = markRecordManager.fetchSearchByConds(conds);
			if (markRecord != null) {
				dto.setMarkRecordId(markRecord.getId());
			}
		}
		return dto;
	}

	@Override
	public StudentDocumentDto addStudentDocument(StudentDocumentBean studentDocumentBean) {
		if (studentDocumentBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Byte actived = 1;
		Conds conds = new Conds();
		conds.equal("student_id", studentDocumentBean.getStudentId());
		conds.equal("file_id", studentDocumentBean.getFileId());
		conds.rightLike("name", studentDocumentBean.getName());
		conds.equal("document_id", studentDocumentBean.getDocumentId());
		conds.equal("actived", actived);
		int documentCount = this.count(conds);
		if (documentCount != 0) {
			studentDocumentBean.setName(studentDocumentBean.getName() + "_" + documentCount);
		}
		StudentDocument studentDocument;
		studentDocument = studentDocumentBean.transToStudentDocument();
		studentDocument.setActived(actived);
		Timestamp createdAt = DateUtil.getSqlTimestamp();
		studentDocument.setCreatedAt(createdAt);
		studentDocument.setUpdatedAt(createdAt);
		studentDocument.setPageCount(-1);
		assignDcoumentAttribute(studentDocumentBean, studentDocument);
		this.logger.info("studentId:" + studentDocumentBean.getStudentId());
		if (studentDocumentBean.getStudentId() != null) {
			Student student = studentMapper.fetchById(studentDocumentBean.getStudentId());
			if (student != null) {
				studentDocument.setStudentNickName(student.getNickName());// nickName
				studentDocument.setStudentId(student.getId());
			}
		}
		if (studentDocumentBean.getDirectoryId() != null) {
			Directory directory = directoryMapper.fetchById(studentDocumentBean.getDirectoryId());
			if (directory == null) {
				throw DirectoryErrors.getInstance().directoryNotExistsError();
			}
			if (!directory.getStudentId().equals(studentDocumentBean.getStudentId())) {
				throw BusinessErrors.getInstance().userNoAuthError();
			}
			studentDocument.setDirectoryId(studentDocumentBean.getDirectoryId());
		}
		this.logger.info("studentId:" + studentDocument.getStudentId());
		Byte preview = 0;
		studentDocument.setPreview(preview);

		if (studentDocument.getPermission() == null) {
			studentDocument.setPermission(PermissionType.PERMISSION_PUBLIC);// 公开
		}
		if (studentDocumentBean.getReviewState() != null) {
			studentDocument.setReviewState(studentDocumentBean.getReviewState());
		} else {
			studentDocument.setReviewState(Byte.valueOf("2"));// 审核通过,默认审核通过,管理员可下架
		}
		studentDocument.setPreview(Byte.valueOf("1"));// 可预览
		if (studentDocument.getPageCount() == null) {
			studentDocument.setPageCount(-1);
		}
		if (studentDocument.getRatings() == null) {
			studentDocument.setRatings(0);
		}
		if(studentDocument.getDirectoryId()==null){
			studentDocument.setDirectoryId(Long.valueOf(0));
		}
		this.save(studentDocument);
		this.addOrUpdateTags(studentDocument);
		int item = 0;// 积分补全信息
		if (studentDocument.getCategoryId() != null) {
			item++;
		}
		if (StringUtils.isNotBlank(studentDocument.getTags())) {
			item++;
		}
		if (StringUtils.isNotBlank(studentDocument.getIntro())
				&& StringUtils.equalsIgnoreCase(studentDocument.getName().trim(), studentDocument.getIntro().trim())) {
			item++;
		}
		StudentDocumentDto studentDocumentDto = new StudentDocumentDto(studentDocument);
		// /**
		// * 添加收藏积分
		// */
		// StudentPointLog studentPointLog = studentPointRemoteService.addStudentPointLog(PointTypeEnum.UPLOAD_DOCUMENT, studentDocumentBean.getStudentId(),
		// studentDocument.getId(), item);
		//
		// if (studentPointLog == null) {
		// studentDocumentDto.setPoint(0);
		// studentDocumentDto.setPointMemo(SpringContext.getText("point.mark.no_point.memo"));
		// } else {
		// studentDocumentDto.setPoint(studentPointLog.getChangePoint());
		// }
		/**
		 * 关联表添加数据
		 */
		if (studentDocumentBean.getDirectoryId() == null) {
			studentDocumentBean.setDirectoryId(Long.valueOf(0));
		}
			DirectoryContentRels directoryContentRels = new DirectoryContentRels();
			directoryContentRels.setActived(actived);
			directoryContentRels.setAssociatedId(studentDocument.getId());
			directoryContentRels.setType(SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DOCUMENT);
			directoryContentRels.setAssociatedName(studentDocument.getName());
			if (studentDocument.getFileName() != null) {
				String[] fileName = studentDocument.getFileName().split("\\.");
				directoryContentRels.setExtension(fileName[1]);
				directoryContentRels.setDirectoryId(studentDocument.getDirectoryId());
				directoryContentRels.setStudentId(studentDocument.getStudentId());
			}
			directoryContentRelsMapper.save(directoryContentRels);
		return studentDocumentDto;
	}

	private void addOrUpdateTags(StudentDocument studentDocument) {
		tagsManager.addOrUpdateTags(studentDocument);
	}

	/**
	 * 文档分类等所有
	 *
	 * @param studentDocumentBean
	 * @param studentDocument
	 */
	private void assignDcoumentAttribute(StudentDocumentBean studentDocumentBean, StudentDocument studentDocument) {
		if (studentDocumentBean.getMajorGroupId() != null) {
			MajorGroup majorGroup = majorGroupMapper.fetchById(studentDocumentBean.getMajorGroupId());
			if (majorGroup != null) {
				studentDocument.setMajorGroupId(majorGroup.getId());
			}
		}
		if (studentDocumentBean.getSchoolId() != null) {
			School school = schoolMapper.fetchById(studentDocumentBean.getSchoolId());
			if (school != null) {
				studentDocument.setSchoolId(school.getId());
			}
		}
		if (studentDocumentBean.getCategoryId() != null) {
			Category category = categoryMapper.fetchById(studentDocumentBean.getCategoryId());
			if (category != null) {
				studentDocument.setCategoryId(category.getId());
				studentDocument.setCategoryCode(category.getCode());
			}
		}
		if (studentDocumentBean.getDocumentId() != null) {
			Document document = documentMapper.fetchById(studentDocumentBean.getDocumentId());
			if (document != null) {
				studentDocument.setDocumentId(document.getId());
				studentDocument.setFileName(document.getFileName());
			}
		}
		if (studentDocumentBean.getFileId() != null) {
			File file = fileMapper.fetchById(studentDocumentBean.getFileId());
			if (file != null) {
				studentDocument.setFileId(file.getId());
				studentDocument.setFileKey(file.getKey());
				studentDocument.setPageCount(file.getPageCount());
				logger.info("upload ------- file:" + file.getId().toString() + ",pageCount:" + file.getPageCount());
			}
		}
		Byte reviewState = 1;
		if (studentDocumentBean.getReviewState() != null) {
			reviewState = studentDocumentBean.getReviewState();
			this.logger.info("reviewState:" + reviewState);
		}
		studentDocument.setReviewState(reviewState);
	}

	@Override
	public StudentDocumentDto updateStudentDocument(StudentDocumentBean studentDocumentBean) {
		if (studentDocumentBean == null || studentDocumentBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		StudentDocument studentDocument = this.fetchById(studentDocumentBean.getId());
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().fileNotFound();
		}

		if (studentDocumentBean.getStudentId() != null && !studentDocumentBean.getStudentId().equals(studentDocument.getStudentId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}

		OperationRecord operationRecord = new OperationRecord();
		operationRecord.setAssociatedId(studentDocument.getId());
		operationRecord.setStudentDocumentId(studentDocument.getId());
		operationRecord.setData(JsonUtil.toJsonString(studentDocument));
		operationRecord.setDataType(SuimeLibraryConstants.DOC_OPERATE_DATA_TYPE_RECORD_UPLOAD);
		operationRecord.setOperateType(SuimeLibraryConstants.DOC_OPERATE_OP_TYPE_RECORD_UPDATE);
		operationRecord.setCreatedAt(DateUtil.getSqlTimestamp());
		operationRecord.setStudentId(studentDocumentBean.getStudentId());
		operationRecord.setActived(SuimeLibraryConstants.COMMON_ACTIVED_VALID);
		operationRecordMapper.save(operationRecord);

		if (StringUtils.isNotBlank(studentDocumentBean.getName())) {
			studentDocument.setName(studentDocumentBean.getName());
		}
		studentDocument.setIntro(studentDocumentBean.getIntro());
		if (studentDocumentBean.getPrice() != null) {
			studentDocument.setPrice(studentDocumentBean.getPrice());
		}
		if (studentDocumentBean.getRecommended() != null) {
			studentDocument.setRecommended(studentDocumentBean.getRecommended());
		}
		if (studentDocumentBean.getPermission() != null) {
			studentDocument.setPermission(studentDocumentBean.getPermission());
		}
		if (studentDocumentBean.getDirectoryId() != null) {
			Directory directory = directoryMapper.fetchById(studentDocumentBean.getDirectoryId());
			if (directory == null) {
				throw DirectoryErrors.getInstance().directoryNotExistsError();
			}
			if (!directory.getStudentId().equals(studentDocumentBean.getStudentId())) {
				throw BusinessErrors.getInstance().userNoAuthError();
			}
			studentDocument.setDirectoryId(studentDocumentBean.getDirectoryId());
		}
		Timestamp updatedAt = DateUtil.getSqlTimestamp();
		studentDocument.setUpdatedAt(updatedAt);
		assignDcoumentAttribute(studentDocumentBean, studentDocument);
		String tags = studentDocumentBean.getTags();
		String oldTags = studentDocument.getTags();
		if (StringUtils.isNotBlank(tags)) {
			if (!tags.trim().startsWith(Constants.VALUE_SIMPLE_SPLIT_CHAR)) {
				tags = Constants.VALUE_SIMPLE_SPLIT_CHAR + tags;
			}
			if (!tags.endsWith(Constants.VALUE_SIMPLE_SPLIT_CHAR)) {
				tags += Constants.VALUE_SIMPLE_SPLIT_CHAR;
			}
			studentDocument.setTags(tags);
		}
		if (!StringUtils.equals(tags, oldTags)) {
			this.addOrUpdateTags(studentDocument);
		}
		this.update(studentDocument);
		/**
		 *文件夹文档目录关联表记录修改
		 */
		Conds conds = new Conds();
		conds.equal("associated_id", studentDocument.getId());
		conds.equal("student_id", studentDocumentBean.getStudentId());
		conds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DOCUMENT);
		Map<String, Object> direMap = new HashMap<String, Object>();
		direMap.put("conds", conds);
		List<DirectoryContentRels> directoryContentRels = directoryContentRelsMapper.fetchSearchByPage(direMap);
		if (directoryContentRels != null && directoryContentRels.size() > 0) {
			DirectoryContentRels dire = directoryContentRels.get(0);
			dire.setAssociatedName(studentDocumentBean.getName());
			if (studentDocumentBean.getDirectoryId() != null) {
				dire.setDirectoryId(studentDocumentBean.getDirectoryId());
			}
			directoryContentRelsMapper.update(dire);
		}
		return new StudentDocumentDto(studentDocument);
	}

	@Override
	public Page pagePersonalDocumentByItem(StudentDocumentBean studentDocumentBean, int page, int pageSize) {
		Conds conds = new Conds();
		if (studentDocumentBean.getStudentId() != null) {
			conds.equal("sd.student_id", studentDocumentBean.getStudentId());
		}
		if (studentDocumentBean.getActived() != null) {
			conds.equal("sd.actived", studentDocumentBean.getActived());
		}
		if (studentDocumentBean.getPermission() != null) {
			conds.equal("sd.permission", studentDocumentBean.getPermission());
		}
		if (studentDocumentBean.getReviewState() != null) {
			conds.equal("sd.review_state", studentDocumentBean.getReviewState());
		}
		if (studentDocumentBean.getSource() != null) {
			conds.equal("sd.source", studentDocumentBean.getSource());
		}
		Sort sort = new Sort("sd.type", OrderType.DESC);

		Map<String, Object> params = generateParamsMap(conds, sort, page, pageSize);
		if (studentDocumentBean.getTagsId() != null) {
			params.put("tagsId", studentDocumentBean.getTagsId());
		}
		int count = this.studentDocumentMapper.countSearch(params);

		List<StudentDocumentDto> list = this.studentDocumentMapper.personalDtoSearch(params);
		return generatePage(page, pageSize, count, list);
	}

	@Override
	public Page pagePersonalDocumentWidthIntro(StudentDocumentBean studentDocumentBean, int page, int pageSize) {
		Conds conds = new Conds();
		if (studentDocumentBean.getStudentId() != null) {
			conds.equal("sd.student_id", studentDocumentBean.getStudentId());
		}
		if (studentDocumentBean.getActived() != null) {
			conds.equal("sd.actived", studentDocumentBean.getActived());
		}
		if (studentDocumentBean.getPermission() != null) {
			conds.equal("sd.permission", studentDocumentBean.getPermission());
		}
		if (studentDocumentBean.getReviewState() != null) {
			conds.equal("sd.review_state", studentDocumentBean.getReviewState());
		}
		if (studentDocumentBean.getSource() != null) {
			conds.equal("sd.source", studentDocumentBean.getSource());
		}
		Sort sort = new Sort("sd.type", OrderType.DESC);

		Map<String, Object> params = generateParamsMap(conds, sort, page, pageSize);
		if (studentDocumentBean.getTagsId() != null) {
			params.put("tagsId", studentDocumentBean.getTagsId());
		}
		int count = this.studentDocumentMapper.countSearch(params);

		List<StudentDocumentDto> list = this.studentDocumentMapper.personalDtoWidthIntro(params);
		return generatePage(page, pageSize, count, list);
	}

	@Override
	public Page personalDocumentDir(StudentDocumentBean studentDocumentBean, int page, int pageSize) {
		if (studentDocumentBean.getDirectoryId() != null) {
			Directory directory = this.directoryMapper.fetchById(studentDocumentBean.getDirectoryId());
			if (directory == null || directory.getActived().intValue() != 1) {
				throw DirectoryErrors.getInstance().directoryNotExistsError();
			}
		}
		Conds conds = new Conds();
		if (studentDocumentBean.getStudentId() != null) {
			conds.equal("sd.student_id", studentDocumentBean.getStudentId());
		}
		if (studentDocumentBean.getActived() != null) {
			conds.equal("sd.actived", studentDocumentBean.getActived());
		}
		if (studentDocumentBean.getDirectoryId() != null) {
			conds.equal("sd.directory_id", studentDocumentBean.getDirectoryId());
		} else {
			conds.isNull("sd.directory_id");
		}
		if (studentDocumentBean.getSource() != null) {
			conds.equal("sd.source", studentDocumentBean.getSource());
		}
		Sort sort = null;
		Map<String, Object> params = generateParamsMap(conds, sort, page, pageSize);
		int count = this.studentDocumentMapper.countSearch(params);
		List<StudentDocumentDto> list = this.studentDocumentMapper.personalDtoSearch(params);
		return generatePage(page, pageSize, count, list);
	}

	@Override
	public Page pagePersonalDocumentAll(StudentDocumentBean studentDocumentBean, int page, int pageSize, String sortField, OrderType orderType) {
		if (studentDocumentBean == null) {
			return new Page(pageSize);
		}
		if (studentDocumentBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().userOfflineError();
		}
		Byte actived = studentDocumentBean.getActived();
		if (actived == null) {
			actived = 1;
		}

		Sort sort = null;
		if (StringUtils.isNotBlank(sortField)) {
			sort = new Sort(sortField, orderType);
		}
		Map<String, Object> countMap = new HashMap<String, Object>();
		countMap.put("studentId", studentDocumentBean.getStudentId());
		countMap.put("actived", actived);
		// int totalCount = this.studentDocumentMapper.countWidthMarked(countMap);
		Conds stdocConds = new Conds();
		stdocConds.equal("student_id", studentDocumentBean.getStudentId());
		stdocConds.equal("actived", actived);
		int totalCount = this.count(stdocConds);
		Conds markConds = new Conds();
		markConds.equal("student_id", studentDocumentBean.getStudentId());
		markConds.equal("actived", actived);
		int count = this.markRecordManager.count(markConds);
		totalCount += count;

		Map<String, Object> params = this.generateParamsMap(null, sort, page, pageSize);
		params.put("studentId", studentDocumentBean.getStudentId());
		params.put("actived", actived);
		List<StudentDocumentDto> list = this.studentDocumentMapper.personalDtoWidthMarked(params);

		return generatePage(page, pageSize, totalCount, list);
	}

	@Override
	public List<StudentDocumentDto> fetchRecommendDocuments(Long id, OrderFieldEnum orderFieldEnum, int pageSize) {
		return studentDocumentManager.fetchRecommendDocuments(id, orderFieldEnum, pageSize);
	}

	@Override
	public void changeReviewStateById(Long id, Byte reviewState) {
		if (id == null || reviewState == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		StudentDocument studentDocument = this.fetchById(id);
		if (studentDocument == null) {
			throw BusinessErrors.getInstance().fileNotFound();
		}
		studentDocument.setReviewState(reviewState);
		studentDocument.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.update(studentDocument);
	}

	@Override
	public void changeReviewStateByList(List<Long> ids, Byte reviewState) {
		if (ids == null || ids.isEmpty() || reviewState == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Conds conds = new Conds();
		conds.in("id", ids.toArray(new Long[ids.size()]));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		this.studentDocumentMapper.batchUpdateReviewState(params);
	}

	@Override
	public Page pageAllDocumentByItem(StudentDocumentBean studentDocumentBean, int page, int pageSize, String sortField, OrderType orderType) {
		if (studentDocumentBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		StringBuffer whereBuffer = new StringBuffer();
		List<Object> values = new ArrayList<>();

		Conds countConds = new Conds();
		Conds conds = new Conds();
		Byte actived = 1;
		conds.equal("sd.actived", actived);
		countConds.equal("actived", actived);

		whereBuffer.append(" where sd.actived=1 ");
		if (studentDocumentBean.getReviewState() != null) {
			conds.equal("sd.review_state", studentDocumentBean.getReviewState());
			countConds.equal("review_state", studentDocumentBean.getReviewState());
		}
		if (studentDocumentBean.getStudentId() != null) {
			conds.equal("sd.student_id", studentDocumentBean.getStudentId());
			countConds.equal("student_id", studentDocumentBean.getStudentId());
		}
		if (studentDocumentBean.getSchoolId() != null) {
			conds.equal("sd.school_id", studentDocumentBean.getSchoolId());
			countConds.equal("school_id", studentDocumentBean.getSchoolId());
		}
		Sort sort = null;
		if (StringUtils.isNotBlank(sortField)) {
			if (!sortField.equals("readCount") && !sortField.equals("markCount") && !sortField.equals("createdAt")) {
				sortField = "createdAt";
			}
			OrderType tempOrderType = orderType;
			if (orderType == null) {
				tempOrderType = OrderType.DESC;
			}
			if (orderType != null && !orderType.equals(OrderType.DESC) && !orderType.equals(OrderType.ASC)) {
				tempOrderType = OrderType.DESC;
			}
			sort = new Sort(sortField, tempOrderType);
		}
		Map<String, Object> params = this.generateParamsMap(conds, sort, page, pageSize);
		int totalCount = this.count(countConds);
		List<StudentDocumentDto> list = this.studentDocumentMapper.adminSearchByPage(params);
		return generatePage(page, pageSize, totalCount, list);
	}

	@Override
	public Page adminSearchByItem(SearchAdminBean searchAdminBean, int page, int pageSize) {
		if (searchAdminBean == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Conds conds = new Conds();
		Byte actived = 1;
		conds.equal("sd.actived", actived);
		if (StringUtils.isNotBlank(searchAdminBean.getStudentDocumentName())) {
			conds.like("sd.name", searchAdminBean.getStudentDocumentName());
		}
		if (StringUtils.isNotBlank(searchAdminBean.getName())) {
			conds.like("student.nick_name", searchAdminBean.getName());
		}
		if (searchAdminBean.getCategoryId() != null) {
			Category category = categoryMapper.fetchById(searchAdminBean.getCategoryId());
			if (category != null && category.getActived().equals(actived)) {
				conds.rightLike("sd.category_code", category.getCode());
			}
		}
		if (searchAdminBean.getTagId() != null) {
			Tags tags = tagsManager.fetchById(searchAdminBean.getTagId());
			if (tags != null && actived.equals(tags.getActived())) {
				// Byte tagsType = tags.getType();
				// if (tagsType.equals(Byte.valueOf("1"))) {
				// whereBuffer.append(" and sd.tags like ? ");
				// String tagsText = Constants.KEY_PERCENT + Constants.VALUE_SIMPLE_SPLIT_CHAR + tags.getText() + Constants.VALUE_SIMPLE_SPLIT_CHAR
				// + Constants.KEY_PERCENT;
				// values.add(tagsText);
				// } else if (tagsType.equals(Byte.valueOf("9"))) {
				// Category category = tags.getCategory();
				// if (category != null) {
				// whereBuffer.append(" and sd.category_code like ? ");
				// values.add(category.getCode() + Constants.KEY_PERCENT);
				// }
				// }
				conds.like("sd.tags", Constants.VALUE_SIMPLE_SPLIT_CHAR + tags.getText() + Constants.VALUE_SIMPLE_SPLIT_CHAR);
			}
		}

		if (StringUtils.isNotBlank(searchAdminBean.getTag())) {
			conds.like("sd.tags", Constants.VALUE_SIMPLE_SPLIT_CHAR + searchAdminBean.getTag() + Constants.VALUE_SIMPLE_SPLIT_CHAR);
		}
		Sort sort = null;
		if (StringUtils.isNotBlank(searchAdminBean.getSortField())) {
			OrderType orderType = searchAdminBean.getSort();
			if (orderType == null) {
				orderType = OrderType.DESC;
			}
			sort = new Sort(searchAdminBean.getSortField(), orderType);
		}
		Map<String, Object> params = this.generateParamsMap(conds, sort, page, pageSize);
		int totalCount = this.studentDocumentMapper.countAdminSearch(params);
		List<StudentDocumentDto> list = this.studentDocumentMapper.adminSearchByPage(params);
		return generatePage(page, pageSize, totalCount, list);
	}

	@Override
	public void changeDocsMove(DocumentMoveBean documentBean) {
		Conds conds = new Conds();
		conds.in("id", documentBean.getIds());
		conds.equal("student_id", documentBean.getStudentId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("directoryId", documentBean.getDirectoryId());
		this.studentDocumentMapper.updatedocsdore(params);

		/**
		 * 关联表随之更新
		 */
		Conds relsConds = new Conds();
		relsConds.in("associated_id", documentBean.getIds());
		relsConds.equal("student_id", documentBean.getStudentId());
		relsConds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DOCUMENT);
		Map<String, Object> relsParams = new HashMap<String, Object>();
		relsParams.put("conds", relsConds);
		relsParams.put("directoryId", documentBean.getDirectoryId());
		directoryContentRelsMapper.updateBatch(relsParams);
	}

	/**
	 *更改文档权限
	 */
	@Override
	public void changePermission(DocPermissionBean docPermissionBean) {
		Conds conds = new Conds();
		conds.in("id", docPermissionBean.getIds());
		conds.equal("student_id", docPermissionBean.getStudentId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("permission", docPermissionBean.getPermission());
		studentDocumentMapper.changepermission(params);
	}

	@Override
	public List<StudentDocumentDto> getAllStudentDocument() {
		return this.studentDocumentMapper.fetchDtoSearch(new HashMap<String, Object>());
	}

	@Override
	public void updateByRels(StudentDocument studentDocument) {
		this.update(studentDocument);
		Conds conds = new Conds();
		conds.equal("associated_id", studentDocument.getId());
		conds.equal("actived", "1");
		conds.equal("student_id", studentDocument.getStudentId());
		conds.equal("type", SuimeLibraryConstants.DIR_CONTENT_RELS_TYPE_DOCUMENT);
		Map<String, Object> direMap = new HashMap<String, Object>();
		direMap.put("conds", conds);
		List<DirectoryContentRels> directoryContentRels = directoryContentRelsMapper.fetchSearchByPage(direMap);
		if (directoryContentRels != null && directoryContentRels.size() > 0) {
			DirectoryContentRels dire = directoryContentRels.get(0);
			dire.setActived(studentDocument.getActived());
			directoryContentRelsMapper.update(dire);
		}
	}

	@Override
	public Page pageByItemDissertation(SearchBean searchBean, int curPage, int pageSize) {
		Long studentId = searchBean.getStudentId();
		Map<String, Object> params = extractSchool(studentId);
		Conds conds = new Conds();
		if(searchBean.getSchoolId()!=null){
			conds.equal("sd.school_id",searchBean.getSchoolId());
		}
		if(searchBean.getSchoolIds()!=null){
			conds.in("sd.school_id", searchBean.getSchoolIds());
		}
		conds.equal("sd.review_state", 2);
		conds.equal("sd.actived", 1);
		conds.equal("sd.permission", 1);
		conds.equal("file.state", 3);
		conds.notNull("file.page_Count");
		params.put("conds", conds);
		params.put("offset", curPage > 0 ? ((curPage - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		String sortField = searchBean.getSortField();
		if (searchBean.getSort() == null) {
			searchBean.setSort(OrderType.DESC);
		}
		if (StringUtils.isBlank(sortField)) {
			sortField = OrderFieldEnum.READ_COUNT.name();
		}
		if(searchBean.getTagId()!=null){
			params.put("tagId",searchBean.getTagId());
		}
		Sort sort = new Sort(sortField, searchBean.getSort());
		params.put("sort", sort);
		int totalCount = this.studentDocumentMapper.fetchDtoSearchDissertationCount(params);
		List<StudentDocumentDto> list = this.studentDocumentMapper.fetchDtoSearchDissertation(params);
		return generatePage(curPage, pageSize, totalCount, list);
	}
}

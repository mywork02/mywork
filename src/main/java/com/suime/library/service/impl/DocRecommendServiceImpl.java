package com.suime.library.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.DocRecommend;
import com.suime.context.model.StudentDocument;
import com.suime.library.dao.DocRecommendMapper;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.manager.StudentDocumentManager;
import com.suime.library.service.DocRecommendService;

/**
 * docRecommendService
 * Created by ice 29/04/2016.
 */
@Service("docRecommendService")
public class DocRecommendServiceImpl extends GenericServiceImpl<DocRecommend> implements DocRecommendService {

	/**
	 * docRecommendMapper
	 */
	@Autowired
	@Qualifier("docRecommendMapper")
	private DocRecommendMapper docRecommendMapper;

	/**
	 * studentDocumentManager
	 */
	@Autowired
	@Qualifier("studentDocumentManager")
	private StudentDocumentManager studentDocumentManager;

	/**
	 * 需要的记录条数
	 */
	private final int needsNum = 20;

	@Override
	public GenericMapper<DocRecommend> getGenericMapper() {
		return docRecommendMapper;
	}

	@Override
	public List<StudentDocumentDto> fetchRecommendDocuments(Long documentId, Long studentId, int pageSize) {
		if (documentId == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		StudentDocument studentDocument = this.studentDocumentManager.fetchById(documentId);
		if (studentDocument == null || studentDocument.getActived().intValue() != 1) {
			throw BusinessErrors.getInstance().paramsError();
		}
		DocRecommend docRecommend = this.fetchById(documentId);
		if (docRecommend == null || StringUtils.isBlank(docRecommend.getDocIds())) {
			return studentDocumentManager.fetchRecommendDocuments(documentId, null, pageSize);
		}
		String docIds = docRecommend.getDocIds();
		Set<Long> randomIds;
		final int maxNum = 100;// 先取100个出来,在这100个文档内随机抽取
		int limit = maxNum;// 先取100个出来,在这100个文档内随机抽取
		if (studentId == null) {
			randomIds = fetchFirst100IdsBySplit(docIds, limit);
		} else {
			randomIds = extractFromRecommend(studentId, docIds, limit);
		}
		return fetchResultByRandomIds(randomIds, pageSize);
	}

	/**
	 * 从推荐的文档中抽取部分显示
	 * @param studentId
	 * @param docIds
	 * @param limit
	 * @return 前100个文档id中随机抽取的20个id
	 */
	private Set<Long> extractFromRecommend(Long studentId, String docIds, int limit) {
		Set<Long> randomIds;
		List<Long> ids = this.docRecommendMapper.fetchRecommendIds(studentId, docIds, limit);
		if (ids == null || ids.isEmpty()) {
			randomIds = fetchFirst100IdsBySplit(docIds, limit);
		} else {
			ids.remove(ids.size() - 1);
			randomIds = new HashSet<>();
			if (ids.size() <= needsNum) {
				randomIds.addAll(ids);
			} else {
				Collections.shuffle(ids);
				randomIds.addAll(ids.subList(0, needsNum));
			}
		}
		return randomIds;
	}

	/**
	 * 截取前100 的ids 返回前20
	 *
	 * @param docIds
	 * @param limit
	 * @return set 前20的id
	 */
	private Set<Long> fetchFirst100IdsBySplit(String docIds, final int limit) {
		Set<Long> randomIds = new HashSet<>();
		if (StringUtils.isBlank(docIds)) {
			return randomIds;
		}
		int tempLimit = needsNum;
		String[] ids = StringUtils.split(docIds, Constants.VALUE_SIMPLE_SPLIT_CHAR, limit + 2);
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, ids);
		int size = list.size();
		list.remove(size - 1);
		Collections.shuffle(list);
		if (size <= needsNum) {
			tempLimit = size;
		}
		for (int i = 0; i < tempLimit; i++) {
			try {
				randomIds.add(Long.parseLong(list.get(i)));
			} catch (Exception ex) {
				this.logger.error(ex.getMessage());
			}
		}
		return randomIds;
	}

	/**
	 * 根据ids 获取符合条件的stdocDto
	 *
	 * @param randomIds ids
	 * @param pageSize  取用item
	 * @return stdocDto
	 */
	private List<StudentDocumentDto> fetchResultByRandomIds(Set<Long> randomIds, int pageSize) {
		Byte reviewState = 2;// 通过审核
		Byte permission = 1;// 公开
		Byte hasThumbnail = 1;// 有缩略图
		Byte actived = 1;// 有效状态
		Conds conds = new Conds();
		conds.equal("sd.actived", actived);
		conds.equal("sd.review_state", reviewState);
		conds.equal("sd.permission", permission);
		conds.equal("file.has_thumbnail", hasThumbnail);
		conds.notNull("file.page_count");
		conds.in("sd.id", randomIds.toArray(new Long[randomIds.size()]));

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("offset", 0);
		params.put("limit", pageSize);
		return this.docRecommendMapper.fetchStDocDtoSearch(params);
	}

}

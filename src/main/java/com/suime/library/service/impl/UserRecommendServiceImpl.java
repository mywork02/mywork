package com.suime.library.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import com.suime.context.model.UserRecommend;
import com.suime.library.dao.DocRecommendMapper;
import com.suime.library.dao.UserRecommendMapper;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.service.UserRecommendService;

/**
 * userRecommendService
 * Created by ice 13/05/2016.
 */
@Service("userRecommendService")
public class UserRecommendServiceImpl extends GenericServiceImpl<UserRecommend> implements UserRecommendService {

	/**
	 * userRecommendMapper
	 */
	@Autowired
	@Qualifier("userRecommendMapper")
	private UserRecommendMapper userRecommendMapper;

	/**
	 * docRecommendMapper
	 */
	@Autowired
	@Qualifier("docRecommendMapper")
	private DocRecommendMapper docRecommendMapper;

	@Override
	public GenericMapper<UserRecommend> getGenericMapper() {
		return userRecommendMapper;
	}

	@Override
	public List<StudentDocumentDto> fetchRecommendDocuments(Long studentId, int pageSize) {
		if (studentId == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		UserRecommend userRecommend = this.fetchById(studentId);
		if (userRecommend == null || StringUtils.isBlank(userRecommend.getDocIds())) {
			userRecommend = this.fetchById(0L);
		}
		if (userRecommend == null) {
			return new ArrayList<StudentDocumentDto>();
		}
		String docIds = userRecommend.getDocIds();
		final int limit = 50;
		Set<Long> ids = this.fetchFirst100IdsBySplit(docIds, limit, pageSize);
		return fetchResultByRandomIds(ids, pageSize);
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

	/**
	 * 截取前100 的ids 返回前20
	 * @param docIds
	 * @param limit
	 * @param needsNum
	 * @return set 前20的id
	 */
	private Set<Long> fetchFirst100IdsBySplit(String docIds, final int limit, final int needsNum) {
		int tempLimit = limit;
		Set<Long> randomIds = new HashSet<>();
		String[] ids = StringUtils.split(docIds, Constants.VALUE_SIMPLE_SPLIT_CHAR, limit + 2);
		if (ids != null) {
			if (tempLimit >= ids.length) {
				tempLimit = ids.length - 2;
			}
			Random random = new Random();
			while (randomIds.size() < needsNum) {
				int i1 = random.nextInt(limit * limit);
				int index = ((Double) Math.sqrt(i1)).intValue();
				try {
					randomIds.add(Long.parseLong(ids[index]));
				} catch (Exception ex) {
					this.logger.error(ex.getMessage());
				}
			}
		}
		return randomIds;
	}
}

package com.suime.library.service.impl;

import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.library.dto.ActivityDto;
import com.suime.library.dto.pack.ActivityBean;
import com.suime.library.error.ActivityErrors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.ActivityMapper;
import com.suime.context.model.Activity;
import com.suime.library.service.ActivityService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * activityService
 * Created by ice 17/02/2016.
 */
@Service("activityService")
public class ActivityServiceImpl extends GenericServiceImpl<Activity> implements ActivityService {

	/**
	 * 中英文逗号正则表达式
	 */
	private static final String REGEX_COMMA = "[,，]";
	/**
	 * activityMapper
	 */
	@Autowired
	@Qualifier("activityMapper")
	private ActivityMapper activityMapper;

	@Override
	public GenericMapper<Activity> getGenericMapper() {
		return activityMapper;
	}

	@Override
	public List<ActivityDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		params.put("sort", sort);
		return this.activityMapper.fetchDtoSearchByPage(params);
	}

	@Override
	public Page fetchDtoPageSearchByPage(Conds conds, Sort sort, int page, int pageSize) {
		int count = this.count(conds);
		List<ActivityDto> list = this.fetchDtoSearchByPage(conds, sort, page, pageSize);
		return generatePage(page, pageSize, count, list);
	}

	@Override
	public ActivityDto addByItem(ActivityBean activityBean) {
		if (activityBean == null ) {
			throw BusinessErrors.getInstance().paramsError();
		}
		String code = activityBean.getCode();
		if ( StringUtils.isBlank(code) || StringUtils.isBlank(activityBean.getBackground())
				|| StringUtils.isBlank(activityBean.getKeyWords()) || StringUtils.isBlank(activityBean.getMobileBanner())
				|| StringUtils.isBlank(activityBean.getPcBanner())) {
			throw BusinessErrors.getInstance().paramsError();
		}
		if (StringUtils.isBlank(activityBean.getTitle()) || StringUtils.isBlank(activityBean.getSubTitles())) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Conds conds = new Conds();
		conds.equal("code", code);
		if (this.existByConds(conds)) {
			throw ActivityErrors.getInstance().codeAlreadyExistsError(code);
		}
		int wordNum = activityBean.getKeyWords().split(REGEX_COMMA).length;
		int subTitleNum = activityBean.getSubTitles().split(REGEX_COMMA).length;
		if (wordNum != subTitleNum) {
			throw ActivityErrors.getInstance().keyWordsNumberNotEqualSubTitlesNumberError(activityBean.getKeyWords(), activityBean.getSubTitles());
		}
		Activity activity = activityBean.convertToActivity();
		Byte actived = 1;
		Timestamp currentTime = DateUtil.getSqlTimestamp();
		activity.setCreatedAt(currentTime);
		activity.setUpdatedAt(currentTime);
		activity.setActived(actived);
		this.save(activity);
		return new ActivityDto(activity);
	}

	@Override
	public void updateByItem(ActivityBean activityBean) {
		if (activityBean == null || activityBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Activity activity = this.fetchById(activityBean.getId());
		if (activity == null) {
			throw ActivityErrors.getInstance().activityNotFoundError(activityBean.getId());
		}
		if (StringUtils.isNotBlank(activityBean.getBackground())) {
			activity.setBackground(activityBean.getBackground());
		}
		if (StringUtils.isNotBlank(activityBean.getKeyWords())) {
			activity.setKeyWords(activityBean.getKeyWords());
		}
		if (StringUtils.isNotBlank(activityBean.getMobileBanner())) {
			activity.setMobileBanner(activityBean.getMobileBanner());
		}
		if (StringUtils.isNotBlank(activityBean.getPcBanner())) {
			activity.setPcBanner(activityBean.getPcBanner());
		}
		if (activityBean.getOrderNum() != null) {
			activity.setOrderNum(activityBean.getOrderNum());
		}
		String code = activityBean.getCode();
		if (StringUtils.isNotBlank(code) && !code.equals(activity.getCode())) {
			Conds conds = new Conds();
			conds.equal("code", code);
			if (this.existByConds(conds)) {
				throw ActivityErrors.getInstance().codeAlreadyExistsError(code);
			}
			activity.setCode(code);
		}
		if (StringUtils.isNotBlank(activityBean.getTitle())) {
			activity.setTitle(activityBean.getTitle());
		}
		if (StringUtils.isNotBlank(activityBean.getSubTitles())) {
			activity.setSubTitles(activityBean.getSubTitles());
		}
		int wordNum = activity.getKeyWords().split(REGEX_COMMA).length;
		int subTitleNum = activity.getSubTitles().split(REGEX_COMMA).length;
		if (wordNum != subTitleNum) {
			throw ActivityErrors.getInstance().keyWordsNumberNotEqualSubTitlesNumberError(activityBean.getKeyWords(), activityBean.getSubTitles());
		}
		activity.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.update(activity);
	}

}

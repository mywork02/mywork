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
import com.confucian.framework.utils.StringUtils;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Favorite;
import com.suime.library.dao.FavoriteMapper;
import com.suime.library.dao.MarkRecordMapper;
import com.suime.library.dto.FavoriteDto;
import com.suime.library.dto.StudentDocumentDto;
import com.suime.library.dto.pack.FavoriteBean;
import com.suime.library.error.FavoriteErrors;
import com.suime.library.service.FavoriteService;

/**
 * favoriteService
 * Created by chenqy 20/04/2016.
 */
@Service("favoriteService")
public class FavoriteServiceImpl extends GenericServiceImpl<Favorite> implements FavoriteService {

	/**
	 * favoriteMapper
	 */
	@Autowired
	@Qualifier("favoriteMapper")
	private FavoriteMapper favoriteMapper;

	@Autowired
	private MarkRecordMapper markRecordMapper;

	@Override
	public GenericMapper<Favorite> getGenericMapper() {
		return favoriteMapper;
	}

	@Override
	public FavoriteDto add(FavoriteBean favoriteBean) {
		if (favoriteBean == null || favoriteBean.getStudentId() == null || StringUtils.isBlank(favoriteBean.getName())) {
			throw BusinessErrors.getInstance().paramsError();
		}
		if (isExist(favoriteBean)) {
			throw FavoriteErrors.getInstance().allreadyExistError(favoriteBean.getName());
		}
		Favorite favorite = new Favorite();
		Byte actived = 1;
		Timestamp current = DateUtil.getSqlTimestamp();
		favorite.setCreatedAt(current);
		favorite.setUpdatedAt(current);
		favorite.setActived(actived);
		favorite.setName(favoriteBean.getName());
		favorite.setStudentId(favoriteBean.getStudentId());
		favorite.setIntro(favoriteBean.getIntro());
		this.save(favorite);
		return new FavoriteDto(favorite);
	}

	private boolean isExist(FavoriteBean favoriteBean) {
		Conds conds = new Conds();
		Byte actived = 1;
		conds.equal("name", favoriteBean.getName());
		conds.equal("actived", actived);
		conds.equal("student_id", favoriteBean.getStudentId());
		return this.existByConds(conds);
	}

	@Override
	public void remove(Long id, Long studentId) {
		Favorite favorite = this.fetchById(id);
		if (favorite == null || favorite.getActived() == 0) {
			throw FavoriteErrors.getInstance().favoriteNotExistsError();
		}
		if (!favorite.getStudentId().equals(studentId)) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		Timestamp current = DateUtil.getSqlTimestamp();
		Byte actived = 0;
		favorite.setUpdatedAt(current);
		favorite.setActived(actived);
		this.update(favorite);
		favoriteMapper.removeInFavorite(id);
	}

	@Override
	public FavoriteDto updateByItem(FavoriteBean favoriteBean) {
		if (favoriteBean == null || favoriteBean.getId() == null || favoriteBean.getStudentId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Favorite favorite = this.fetchById(favoriteBean.getId());
		if (favorite == null || favorite.getActived().intValue()!=1) {
			throw FavoriteErrors.getInstance().favoriteNotExistsError();
		}
		if (favorite != null && StringUtils.equals(favorite.getName(), favoriteBean.getName()) ) {
			return null;
		}
		if (isExist(favoriteBean)) {
			throw FavoriteErrors.getInstance().allreadyExistError(favoriteBean.getName());
		}
		if (!favorite.getStudentId().equals(favoriteBean.getStudentId())) {
			throw BusinessErrors.getInstance().userNoAuthError();
		}
		if (StringUtils.isNotBlank(favoriteBean.getName())) {
			favorite.setName(favoriteBean.getName());
		}
		favorite.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.update(favorite);
		return new FavoriteDto(favorite);
	}

	@Override
	public Page fetchPersonal(Long studentId,int page,int pageSize) {
		Map<String, Object> params = new HashMap<>();
		Conds conds = new Conds();
		conds.equal("student_id", studentId);
		conds.equal("actived", 1);
		params.put("conds", conds);
		params.put("offset", page);
		params.put("limit", pageSize);
		int totalCount = this.favoriteMapper.fetchDtoCount(params);
		List<FavoriteDto> list = this.favoriteMapper.fetchDtoByItem(params);
		return generatePage(page, pageSize, totalCount, list);
	}

	@Override
	public Page fetchfavoritedoc(Long favoriteId, Long studentId, int page, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("favoriteId", favoriteId);
		params.put("studentId", studentId);
		params.put("offset", page);
		params.put("limit", pageSize);
		int totalCount = this.favoriteMapper.fetchCount(favoriteId);
		List<StudentDocumentDto> list = this.favoriteMapper.fetchfavoritedoc(params);
		return generatePage(page, pageSize, totalCount, list);
	}

	@Override
	public void updateDocToFavorite(Long studentId, Long documentId, Long favoriteId) {
		// 文档是否已在其他收藏夹内
		Conds conds = new Conds();
		conds.equal("actived", "1");
		conds.equal("document_id", documentId);
	}

	@Override
	public void removedoc(Long studentId, Long documentId, Long favoriteId) {
		Conds conds = new Conds();
		conds.equal("actived", "1");
		conds.equal("document_id", documentId);
		conds.equal("favorite_id", favoriteId);
	}
}

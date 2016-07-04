package com.suime.library.service.impl;

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
import com.confucian.mybatis.support.sql.Sort;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Category;
import com.suime.context.model.Student;
import com.suime.context.model.Tags;
import com.suime.library.dao.CategoryMapper;
import com.suime.library.dao.StudentMapper;
import com.suime.library.dao.TagsMapper;
import com.suime.library.dto.TagsDto;
import com.suime.library.dto.pack.TagsBean;
import com.suime.library.error.TagsErrors;
import com.suime.library.service.TagsService;

/**
 * tagsService
 * Created by ice 15/02/2016.
 */
@Service("tagsService")
public class TagsServiceImpl extends GenericServiceImpl<Tags> implements TagsService {

	/**
	 * tagsMapper
	 */
	@Autowired
	@Qualifier("tagsMapper")
	private TagsMapper tagsMapper;

	/**
	 * categoryMapper
	 */
	@Autowired
	@Qualifier("categoryMapper")
	private CategoryMapper categoryMapper;

	/**
	 * studentMapper
	 */
	@Autowired
	@Qualifier("studentMapper")
	private StudentMapper studentMapper;

	@Override
	public GenericMapper<Tags> getGenericMapper() {
		return tagsMapper;
	}

	@Override
	public TagsDto add(TagsBean tagsBean, Byte source, Long userId) {
		if (StringUtils.isEmpty(tagsBean.getText())) {
			throw TagsErrors.getInstance().textIsEmptyError();// 标签文本不能为空
		}
		if (tagsBean.getText().length() > 10) {
			throw TagsErrors.getInstance().textTooLongError();// 标签长度不能超过10
		}
		boolean tagExistsFlag = tagExistsByText(tagsBean, userId);
		if (tagExistsFlag) {
			throw TagsErrors.getInstance().alreadyExistsError(tagsBean.getText());// 该标签已存在
		}

		Tags tags = new Tags();
		Byte available = (byte) 1;
		tags.setActived(available);
		tags.setUserId(userId);
		tags.setSource(source);
		tags.setText(tagsBean.getText());
		tags.setUseCount(0);
		tags.setCreatedAt(DateUtil.getSqlTimestamp());
		tags.setUpdatedAt(DateUtil.getSqlTimestamp());
		tags.setType(tagsBean.getType());
		// if (tagsBean.getCategoryId() != null) {
		// Category category = this.categoryMapper.fetchById(tagsBean.getCategoryId());
		// if (category != null) {
		// tags.setCategoryId(category.getId());
		// }
		// }
		this.tagsMapper.save(tags);
		return new TagsDto(tags);
	}

	/**
	 * 检查当前标签是否已存在
	 * @param bean
	 * @param userId
	 * @return 如果存在则返回true，如果不存在则返回false
	 */
	private boolean tagExistsByText(TagsBean bean, Long userId) {
		Conds conds = new Conds();
		// conds.equal("userId", userId);
		Byte type = 1;
		conds.equal("type", type);
		conds.equal("text", bean.getText());
		return this.count(conds) > 0;
	}

	@Override
	public TagsDto update(TagsBean tagsBean, Byte source, Long userId) {
		if (tagsBean == null || tagsBean.getId() == null || tagsBean.getId() <= 0) {
			throw BusinessErrors.getInstance().paramsError();// 参数错误
		}
		Tags tags = this.fetchById(tagsBean.getId());
		if (tags == null) {
			throw TagsErrors.getInstance().tagsNotFoundError();// 未找到指定的标签
		}
		if (StringUtils.isEmpty(tagsBean.getText())) {
			throw TagsErrors.getInstance().textIsEmptyError();// 标签文本不能为空
		}
		if (tagsBean.getText().length() > 10) {
			throw TagsErrors.getInstance().textTooLongError();// 标签长度不能超过10
		}
		boolean tagExistsFlag = tagExistsByText(tagsBean, userId);
		if (tagExistsFlag) {
			throw TagsErrors.getInstance().alreadyExistsError(tagsBean.getText());// 该标签已存在
		}
		if (tagsBean.getCategoryId() != null) {
			Category category = this.categoryMapper.fetchById(tagsBean.getCategoryId());
			if (category != null) {
				tags.setCategoryId(category.getId());
			}
		}
		tags.setUserId(userId);
		tags.setSource(source);
		tags.setText(tagsBean.getText());
		tags.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.tagsMapper.update(tags);
		return new TagsDto(tags);
	}

	@Override
	public void deleteTag(Long tagId) {
		if (tagId != null) {
			Tags tag = this.fetchById(tagId);
			if (tag != null) {
				Byte unavailable = 0;
				tag.setActived(unavailable);
				tag.setUpdatedAt(DateUtil.getSqlTimestamp());
				this.tagsMapper.update(tag);
			}
		}
	}

	@Override
	public Page getTagList(String text, Integer page, Integer pageSize) {
		Byte available = 1;
		Conds conds = new Conds();
		conds.equal("actived", available);
		if (!StringUtils.isEmpty(text)) {
			conds.like("text", text);
		}
		int count = this.count(conds);
		List<TagsDto> list = this.fetchDtoSearchByPage(conds, null, page, pageSize);
		Page pagedData = generatePage(page, pageSize, count, list);
		return pagedData;
	}

	@Override
	public List<TagsDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		params.put("sort", sort);
		return this.tagsMapper.fetchDtoSearchByPage(params);
	}

	@Override
	public List<TagsDto> fetchDto(Conds conds, Sort sort, int page, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);
		params.put("sort", sort);
		return this.tagsMapper.fetchDto(params);
	}

	@Override
	public List<TagsDto> findDtoByItem(TagsBean tagsBean, int pageSize) {
		Conds conds = new Conds();
		Byte available = 1;
		conds.equal("actived", available);
		if (tagsBean.getType() != null) {
			conds.equal("type", tagsBean.getType());
		}
		List<TagsDto> list = this.fetchDtoSearchByPage(conds, null, 1, pageSize);
		return list;
	}

	@Override
	public List<TagsDto> findDtoByStudentId(Long studentId, int pageSize) {
		Long tempStudentId = studentId;
		tempStudentId = 21L;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", tempStudentId);
		params.put("offset", 0);
		params.put("limit", pageSize > 0 ? pageSize : 0);

		List<TagsDto> list = this.tagsMapper.fetchDtoByDocStudent(params);

		return list;
	}

	@Override
	public List<TagsDto> findDtoByBuildingId(Long buildingId, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buildingId", buildingId);
		List<Student> students = studentMapper.fetchByBuilding(params);
		if (students == null || students.isEmpty()) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Long studentId = students.get(0).getId();
		return this.findDtoByStudentId(studentId, pageSize);
	}

	@Override
	public List<TagsDto> findDissertation() {
		return tagsMapper.fetchDtoByDissertation();
	}
}

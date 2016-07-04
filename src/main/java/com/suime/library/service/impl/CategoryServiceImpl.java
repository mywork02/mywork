package com.suime.library.service.impl;

import com.confucian.framework.support.Page;
import com.confucian.framework.utils.DateUtil;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.error.BusinessErrors;
import com.suime.context.model.Category;
import com.suime.library.dao.CategoryMapper;
import com.suime.library.dto.CategoryDto;
import com.suime.library.dto.pack.CategoryBean;
import com.suime.library.error.CategoryErrors;
import com.suime.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * categoryService
 * Created by ice 17/02/2016.
 */
@Service("categoryService")
public class CategoryServiceImpl extends GenericServiceImpl<Category> implements CategoryService {

	/**
	 * parent_id 字段
	 */
	private static final String PARENT_ID = "parent_id";
	/**
	 * categoryMapper
	 */
	@Autowired
	@Qualifier("categoryMapper")
	private CategoryMapper categoryMapper;

	@Override
	public GenericMapper<Category> getGenericMapper() {
		return categoryMapper;
	}

	@Override
	public List<CategoryDto> findTopLevelCategory() {
		Conds conds = new Conds();
		conds.isNull("t.parent_id");
		conds.equal("t.level", 1);
		conds.equal("t.actived", 1);

		List<Category> list = this.fetchSearchByPage(conds, null, 0, 0);
		if (list != null && !list.isEmpty()) {
			List<CategoryDto> result = new ArrayList<CategoryDto>();
			for (Category category : list) {
				CategoryDto categoryDto = trans2Dto(category);
				result.add(categoryDto);
			}
			return result;
		}
		return null;
	}

	/**
	 * trans to dto
	 * @param category
	 * @return category dto
	 */
	private CategoryDto trans2Dto(Category category) {
		CategoryDto categoryDto = new CategoryDto(category);
		if (category.getLevel().intValue() < 3) {
			int level = category.getLevel() + 1;
			Conds conds = new Conds();
			conds.equal("t.parent_id", category.getId());
			conds.equal("t.level", level);
			conds.equal("t.actived", 1);

			List<Category> list = this.fetchSearchByPage(conds, null, 0, 0);
			// this.setChildren(children2);
			if (list != null && !list.isEmpty()) {
				List<CategoryDto> dtos = new ArrayList<CategoryDto>();
				for (Category temp : list) {
					if (temp.getActived().intValue() == 1) {
						dtos.add(trans2Dto(temp));
					}
				}
				categoryDto.setChildren(dtos);
			}
		}
		return categoryDto;
	}

	@Override
	public CategoryDto addCategory(CategoryBean categoryBean) {
		if (categoryBean == null || categoryBean.getCode() == null || categoryBean.getName() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Conds conds = new Conds();
		conds.equal("name", categoryBean.getName());
		conds.equal(PARENT_ID, categoryBean.getParentId());
		Category temp = this.fetchSearchByConds(conds);
		if (temp != null) {
			throw CategoryErrors.getInstance().categoryAlreadyExistsError();// 同级目录下已存在同名目录
		}
		Conds tempConds = new Conds();
		tempConds.equal("code", categoryBean.getCode());
		Category tempByCode = this.fetchSearchByConds(conds);

		if (tempByCode != null) {
			throw CategoryErrors.getInstance().codeAlreadyExistsError(categoryBean.getCode());// 目录编码已存在
		}
		Category category = categoryBean.transToCategory();
		Category parent = null;
		category.setParentId(categoryBean.getParentId());
		if (categoryBean.getParentId() != null) {
			parent = this.fetchById(categoryBean.getParentId());
			if (parent == null) {
				// 抛出异常 父分类不存在
				throw CategoryErrors.getInstance().categoryNotFoundError();// 未找到指定的目录
			}
		}
		Byte level = parent == null ? 1 : (byte) (parent.getLevel() + 1);
		category.setLevel(level);
		category.setCode(categoryBean.getCode());
		category.setName(categoryBean.getName());
		category.setRemarks(categoryBean.getRemarks());
		category.setCreatedAt(DateUtil.getSqlTimestamp());
		category.setUpdatedAt(DateUtil.getSqlTimestamp());
		Byte available = (byte) 1;
		category.setActived(available);
		this.save(category);
		return new CategoryDto(category);
	}

	@Override
	public CategoryDto updateCategory(CategoryBean categoryBean) {
		if (categoryBean == null || categoryBean.getId() == null) {
			throw BusinessErrors.getInstance().paramsError();
		}
		Category category = this.fetchById(categoryBean.getId());
		if (category == null) {
			throw CategoryErrors.getInstance().categoryNotFoundError();// 未找到指定的目录
		}

		Conds conds = new Conds();
		conds.equal("name", categoryBean.getName());
		conds.equal(PARENT_ID, categoryBean.getParentId());
		Category temp = this.fetchSearchByConds(conds);
		if (temp != null) {
			throw CategoryErrors.getInstance().categoryAlreadyExistsError();// 同级目录下已存在同名目录
		}

		Conds tempConds = new Conds();
		tempConds.equal("code", categoryBean.getCode());
		tempConds.notEqual("id", category.getId());
		Category tempByCode = this.fetchSearchByConds(conds);
		if (tempByCode != null) {
			throw CategoryErrors.getInstance().codeAlreadyExistsError(categoryBean.getCode());// 目录编码已存在
		}

		Category parent = null;
		if (categoryBean.getParentId() != null) {
			parent = this.fetchById(categoryBean.getParentId());
			if (parent != null) {
				category.setParentId(parent.getId());
			} else {
				throw CategoryErrors.getInstance().categoryNotFoundError();// 未找到指定的目录
			}
		}
		if (category.getLevel() > 1) {
			parent = this.fetchById(category.getParentId());
		}

		Byte level = 1;
		Long parentId = null;
		if (parent != null) {
			level = (byte) (parent.getLevel() + 1);
			parentId = parent.getId();
		}
		category.setParentId(parentId);
		category.setLevel(level);

		category.setName(categoryBean.getName());
		category.setCode(categoryBean.getCode());
		category.setRemarks(categoryBean.getRemarks());
		category.setUpdatedAt(DateUtil.getSqlTimestamp());
		this.categoryMapper.updateForcibly(category);
		return new CategoryDto(category);
	}

	@Override
	public void deleteCategory(Long id) {
		if (id != null) {
			Category category = this.fetchById(id);
			if (category != null) {
				Byte unavailable = (byte) 0;
				category.setActived(unavailable);
				this.update(category);
			}
		}
	}

	@Override
	public List<CategoryDto> findDtoByItem(CategoryBean categoryBean, boolean allFlag) {
		if (categoryBean == null) {
			return null;
		}
		Conds conds = new Conds();
		if (categoryBean.getActived() != null) {
			conds.equal("actived", categoryBean.getActived());
		} else {
			conds.equal("actived", (byte) 1);
		}
		if (categoryBean.getParentId() != null) {
			conds.equal(PARENT_ID, categoryBean.getParentId());
		} else {
			conds.isNull(PARENT_ID);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("conds", conds);
		params.put("limit", 0);
		List<CategoryDto> list = this.categoryMapper.fetchDtoSearchByPage(params);
		if (allFlag) {
			if (list != null && !list.isEmpty()) {
				for (CategoryDto dto : list) {
					CategoryBean tempBean = new CategoryBean();
					tempBean.setParentId(dto.getId());
					List<CategoryDto> tempList = this.findDtoByItem(tempBean, allFlag);
					dto.setChildren(tempList);
				}
			}
		}
		return list;
	}

	@Override
	public Page statisticCountByCategory(Integer page, Integer pageSize) {
		int count = this.count(null);
		List<Map<String, Object>> list = this.categoryMapper.statisticCountByCategory(null);
		return generatePage(page, pageSize, count, list);
	}
}

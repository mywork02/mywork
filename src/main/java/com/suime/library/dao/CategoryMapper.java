package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Category;
import com.suime.library.dto.CategoryDto;

import java.util.List;
import java.util.Map;

/**
 * categoryMapper
 * Created by ice 17/02/2016.
 */
public interface CategoryMapper extends GenericMapper<Category> {

    /**
     * 分页查询dto
     *
     * @param params
     * @return list category dto
     */
    List<CategoryDto> fetchDtoSearchByPage(Map<String, Object> params);

    /**
     * 强制更新所有字段,调用该方法时需要先从数据库中获取当前记录,然后在做update操作
     *
     * @param category category
     * @return 更新条数
     */
    int updateForcibly(Category category);

    /**
     * 统计各分类下文档的数量
     *
     * @param params
     * @return list map
     */
    List<Map<String, Object>> statisticCountByCategory(Map<String, Object> params);

}

package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.suime.context.model.Category;
import com.suime.library.dto.CategoryDto;
import com.suime.library.dto.pack.CategoryBean;

import java.util.List;

/**
 * categoryService
 * Created by ice 17/02/2016.
 */
public interface CategoryService extends GenericService<Category> {

    /**
     * 获取顶级分类
     *
     * @return 顶级分类list
     */
    List<CategoryDto> findTopLevelCategory();

    /**
     * 添加分类
     *
     * @param categoryBean categoryBean
     * @return 分类 dto
     */
    CategoryDto addCategory(CategoryBean categoryBean);

    /**
     * 修改分类
     *
     * @param categoryBean categoryBean
     * @return dto
     */
    CategoryDto updateCategory(CategoryBean categoryBean);

    /**
     * 删除目录
     *
     * @param id 目录id
     */
    void deleteCategory(Long id);


    /**
     * findDtoByItem
     *
     * @param categoryBean categoryBean
     * @param allFlag      是否加载所有子分类
     * @return list categoryDto
     */
    List<CategoryDto> findDtoByItem(CategoryBean categoryBean, boolean allFlag);

    /**
     * 统计各分类下文档的数量
     *
     * @param page     当前页
     * @param pageSize 每页显示条数
     * @return page
     */
    Page statisticCountByCategory(Integer page, Integer pageSize);

}

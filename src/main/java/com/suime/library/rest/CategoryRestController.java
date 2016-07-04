package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.utils.JsonUtil;
import com.confucian.mybatis.support.sql.Conds;
import com.suime.common.cache.CacheService;
import com.suime.context.model.Category;
import com.suime.library.dto.CategoryDto;
import com.suime.library.dto.pack.CategoryBean;
import com.suime.library.error.CategoryErrors;
import com.suime.library.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.confucian.framework.web.AbstractRestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * categoryRestController
 *
 * @author ice
 */
@Api(description = "分类接口", tags = "category")
@RestController
@RequestMapping("/category")
public class CategoryRestController extends AbstractRestController {

    /**
     * categoryService
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * cacheService
     */
    @Autowired
    private CacheService cacheService;

    /**
     * 顶层category缓存key
     */
    private final String KEY_TOP_LEVEL_CATEGORY = "key_top_level_category";

    /**
     * 分类 key_category
     */
    private final String KEY_CATEGORY = "key_category_";

    /**
     * 获取所有顶级目录
     *
     * @return resultBean
     */
    @ApiOperation(value = "获取所有顶级分类")
    @RequestMapping(path = "/findTopLevelCategory", method = {RequestMethod.GET})
    public Object findTopLevelCategory() {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Object categorysStr = cacheService.get(KEY_TOP_LEVEL_CATEGORY);
        final int exp = 60 * 60 * 24;   //一天
        if (categorysStr == null) {
            List<CategoryDto> topLevelCategory = this.categoryService.findTopLevelCategory();
            cacheService.set(KEY_TOP_LEVEL_CATEGORY, exp * 7, JsonUtil.toJsonString(topLevelCategory));
            cacheService.set(KEY_TOP_LEVEL_CATEGORY + "_time", exp * 6, 1);
            resultBean.setBody(topLevelCategory);
        } else {
            if (cacheService.get(KEY_TOP_LEVEL_CATEGORY + "_time") == null) {
                cacheService.set(KEY_TOP_LEVEL_CATEGORY + "_time", exp * 6, 1);
                cacheService.set(KEY_TOP_LEVEL_CATEGORY, exp * 7, categorysStr);
            }
            resultBean.setBody(JsonUtil.toJsonArray(categorysStr));
        }
        return resultBean;
    }

    /**
     * 获取所有顶级目录
     *
     * @return resultBean
     */
    @ApiOperation(value = "获取所有顶级分类，刷新缓存")
    @RequestMapping(path = "/refreshTopLevelCategory", method = {RequestMethod.POST})
    public Object refreshTopLevelCategory() {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Object categorysStr = cacheService.get(KEY_TOP_LEVEL_CATEGORY);
        final int exp = 60 * 60 * 24;   //一天
        List<CategoryDto> topLevelCategory = this.categoryService.findTopLevelCategory();
        cacheService.set(KEY_TOP_LEVEL_CATEGORY, exp * 7, JsonUtil.toJsonString(topLevelCategory));
        cacheService.set(KEY_TOP_LEVEL_CATEGORY + "_time", exp * 6, 1);
        resultBean.setBody(topLevelCategory);
        return resultBean;
    }

    /**
     * 添加分类
     *
     * @param categoryBean category bean
     * @return resultBean
     */
    @ApiOperation(value = "添加分类")
    @RequiresAuthentication
    @RequestMapping(path = "/add", method = {RequestMethod.POST})
    public Object add(@RequestBody CategoryBean categoryBean) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.categoryService.addCategory(categoryBean));
        return resultBean;
    }

    /**
     * 更新目录信息
     *
     * @param categoryBean category bean
     * @return resultBean
     */
    @ApiOperation(value = "更新分类信息")
    @RequiresAuthentication
    @RequestMapping(path = "/update", method = {RequestMethod.POST})
    public Object update(@RequestBody CategoryBean categoryBean) {
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(this.categoryService.updateCategory(categoryBean));
        return resultBean;
    }

    /**
     * 删除分类
     *
     * @param id 目录id
     * @return resultBean
     */
    @ApiOperation(value = "删除分类")
    @RequiresAuthentication
    @RequestMapping(path = "/delete/{id}", method = {RequestMethod.DELETE})
    public Object delete(@PathVariable("id") Long id) {
        this.categoryService.deleteCategory(id);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        return resultBean;
    }

    /**
     * 获取目录详细信息
     *
     * @param id 目录id
     * @return resultBean
     */
    @ApiOperation(value = "获取分类详细信息")
    @RequestMapping(path = "/info/{id}", method = {RequestMethod.GET})
    public Object getinfo(@PathVariable("id") Long id) {
        Category category = this.categoryService.fetchById(id);
        if (category == null || category.getActived().intValue() == 0) {
            throw CategoryErrors.getInstance().categoryNotFoundError();// 未找到指定的目录
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);

        Object cacheCategory = cacheService.get(KEY_CATEGORY + id.toString());
        final int exp = 60 * 60 * 24;   //一天
        if (cacheCategory == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", category.getId());
            map.put("name", category.getName());
            map.put("level", category.getLevel());
            int level = category.getLevel();
            if (level > 1) {
                Map<String, Object> parent = new HashMap<>();
                Category tempCateory = category;
                for (int i = 0; i < level - 1; i++) {
                    tempCateory = this.categoryService.fetchById(tempCateory.getParentId());
                    if (tempCateory != null) {
                        int tempLevel = (level - i - 1);
                        parent.put("parentId_" + tempLevel, tempCateory.getId());
                        parent.put("parentName_" + tempLevel, tempCateory.getName());
                    }
                }
                map.put("parent", parent);
            }
            CategoryBean categoryBean = new CategoryBean();
            categoryBean.setParentId(id);
            List<CategoryDto> list = this.categoryService.findDtoByItem(categoryBean, false);
            map.put("children", list);
            resultBean.setBody(map);
            cacheService.set(KEY_CATEGORY + id.toString(), exp * 7, JsonUtil.toJsonString(map));
            cacheService.set(KEY_CATEGORY + "time_" + id.toString(), exp * 6, 1);
        } else {
            if (cacheService.get(KEY_CATEGORY + "time_" + id.toString()) == null) {
                cacheService.set(KEY_CATEGORY + "time_" + id.toString(), exp * 6, 1);
                cacheService.set(KEY_CATEGORY + id.toString(), exp * 7, cacheCategory);
            }
            resultBean.setBody(JsonUtil.toJsonObject(cacheCategory));
        }
        return resultBean;
    }

    /**
     * 获取子目录详细信息
     *
     * @param id 目录id
     * @return resultBean
     */
    @ApiOperation(value = "获取下一级子分类信息")
    @RequestMapping(path = "/subList/{id}", method = {RequestMethod.GET})
    public Object subList(@PathVariable("id") Long id) {
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setParentId(id);
        List<CategoryDto> list = this.categoryService.findDtoByItem(categoryBean, false);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(list);
        return resultBean;
    }

    /**
     * 获取所有子目录详细信息，包括子目录的子目录
     *
     * @param id 目录id
     * @return resultBean
     */
    @ApiOperation(value = "获取所有子分类信息，包括子分类的子分类")
    @RequestMapping(path = "/allSubList/{id}", method = {RequestMethod.GET})
    public Object allSubList(@PathVariable("id") Long id) {
        CategoryBean categoryBean = new CategoryBean();
        categoryBean.setParentId(id);
        List<CategoryDto> list = this.categoryService.findDtoByItem(categoryBean, true);
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        resultBean.setBody(list);
        return resultBean;
    }

}

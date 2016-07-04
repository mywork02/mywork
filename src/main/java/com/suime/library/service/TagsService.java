package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.context.model.Tags;
import com.suime.library.dto.TagsDto;
import com.suime.library.dto.pack.TagsBean;

import java.util.List;

/**
 * tagsService
 * Created by ice 15/02/2016.
 */
public interface TagsService extends GenericService<Tags> {
    /**
     * 添加标签
     *
     * @param tagsBean tagsBean
     * @param source   来源,1:管理员添加,2:用户添加
     * @param userId   用户id
     * @return 标签详情
     */
    TagsDto add(TagsBean tagsBean, Byte source, Long userId);

    /**
     * 修改标签
     *
     * @param tagsBean tagsBean
     * @return 标签详情
     */
    TagsDto update(TagsBean tagsBean, Byte source, Long userId);

    /**
     * 删除标签
     *
     * @param tagId 标签id
     */
    void deleteTag(Long tagId);

    /**
     * 获取标签分页数据
     *
     * @param text     搜索的文本
     * @param page     当前页
     * @param pageSize 分页大小
     * @return 分页数据
     */
    Page getTagList(String text, Integer page, Integer pageSize);

    /**
     * 分页查询 dto
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return list tagsDto
     */
    List<TagsDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize);

    /**
     * 根据类别id,获取类别所包含的所有标签,仅有 id,source,text,useCount 属性
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return list tagsDto
     */
    List<TagsDto> fetchDto(Conds conds, Sort sort, int page, int pageSize);

    /**
     * findDtoByItem
     *
     * @param tagsBean tagsBean
     * @param pageSize pageSize
     * @return list tagsDto
     */
    List<TagsDto> findDtoByItem(TagsBean tagsBean, int pageSize);

    /**
     * 用户所使用的tags
     *
     * @param studentId
     * @param pageSize
     * @return
     */
    List<TagsDto> findDtoByStudentId(Long studentId, int pageSize);

    /**
     * 根据楼栋,获取楼长用户所使用的tags
     *
     * @param buildingId
     * @param pageSize
     * @return
     */
    List<TagsDto> findDtoByBuildingId(Long buildingId, int pageSize);
    
    /**
     * 获取专题标签
     */
    List<TagsDto> findDissertation();
}

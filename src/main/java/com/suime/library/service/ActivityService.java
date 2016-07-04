package com.suime.library.service;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.service.GenericService;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.context.model.Activity;
import com.suime.library.dto.ActivityDto;
import com.suime.library.dto.pack.ActivityBean;

import java.util.List;

/**
 * activityService
 * Created by ice 17/02/2016.
 */
public interface ActivityService extends GenericService<Activity> {

    /**
     * 分页查询 dto
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return list acticityDto
     */
    List<ActivityDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize);

    /**
     * 分页查询 dto
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return page
     */
    Page fetchDtoPageSearchByPage(Conds conds, Sort sort, int page, int pageSize);

    /**
     * 添加
     *
     * @param activityBean activityBean
     * @return activityDto
     */
    ActivityDto addByItem(ActivityBean activityBean);

    /**
     * 更新信息
     *
     * @param activityBean activityBean
     */
    void updateByItem(ActivityBean activityBean);

}

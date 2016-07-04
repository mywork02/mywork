package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Activity;
import com.suime.library.dto.ActivityDto;

import java.util.List;
import java.util.Map;

/**
 * activityMapper
 * Created by ice 17/02/2016.
 */
public interface ActivityMapper extends GenericMapper<Activity> {

    /**
     * 分页查询 dto
     *
     * @param params
     * @return list activityDto
     */
    List<ActivityDto> fetchDtoSearchByPage(Map<String, Object> params);

}

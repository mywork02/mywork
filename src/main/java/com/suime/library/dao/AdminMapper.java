package com.suime.library.dao;

import java.util.List;
import java.util.Map;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Admin;
import com.suime.library.dto.AdminDto;

/**
 * adminMapper
 * Created by ice 17/02/2016.
 */
public interface AdminMapper extends GenericMapper<Admin> {

    /**
     * 分页dto查询
     *
     * @param params
     * @return list admin dto
     */
    List<AdminDto> fetchDtoSearch(Map<String, Object> params);

    /**
     * 查询和role关联查询的记录总数
     *
     * @return 和role关联查询的记录总数
     */
    int countWidthRole(Map<String, Object> params);

}

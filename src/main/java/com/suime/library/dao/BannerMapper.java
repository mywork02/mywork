package com.suime.library.dao;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.suime.context.model.Banner;
import com.suime.library.dto.BannerDto;

import java.util.List;
import java.util.Map;

/**
 * bannerMapper
 * Created by ice 09/03/2016.
 */
public interface BannerMapper extends GenericMapper<Banner> {

    /**
     * 分页查询 dto
     *
     * @param params
     * @return list banner dto
     */
    List<BannerDto> fetchDtoSearchByPage(Map<String, Object> params);

}

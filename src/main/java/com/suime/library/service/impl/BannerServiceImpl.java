package com.suime.library.service.impl;

import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.library.dto.BannerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.impl.GenericServiceImpl;
import com.suime.library.dao.BannerMapper;
import com.suime.context.model.Banner;
import com.suime.library.service.BannerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * bannerService
 * Created by ice 09/03/2016.
 */
@Service("bannerService")
public class BannerServiceImpl extends GenericServiceImpl<Banner> implements BannerService {

    /**
     * bannerMapper
     */
    @Autowired
    @Qualifier("bannerMapper")
    private BannerMapper bannerMapper;

    @Override
    public GenericMapper<Banner> getGenericMapper() {
        return bannerMapper;
    }

    @Override
    public List<BannerDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
        params.put("limit", pageSize > 0 ? pageSize : 0);
        params.put("sort", sort);
        return this.bannerMapper.fetchDtoSearchByPage(params);
    }
}

package com.suime.library.service;

import com.confucian.mybatis.support.service.GenericService;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.context.model.Banner;
import com.suime.library.dto.BannerDto;

import java.util.List;

/**
 * bannerService
 * Created by ice 09/03/2016.
 */
public interface BannerService extends GenericService<Banner> {

    /**
     * 分页查询 dto
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return list bannerDto
     */
    List<BannerDto> fetchDtoSearchByPage(Conds conds, Sort sort, int page, int pageSize);

}

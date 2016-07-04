package com.suime.library.rest;

import com.confucian.framework.dto.CommonResultBean;
import com.confucian.framework.support.Constants;
import com.confucian.framework.web.AbstractRestController;
import com.confucian.mybatis.support.scope.OrderType;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import com.suime.context.model.support.LibraryConstants;
import com.suime.library.dto.BannerDto;
import com.suime.library.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ice on 9/12/2015.
 */
@Api(description = "banner接口", tags = "index")
@RestController
@RequestMapping("/index")
public class IndexRestController extends AbstractRestController {

    @Autowired
    private BannerService bannerService;

    private final Integer maxBannerLimit = 10;

    /**
     * 文库移动版banner
     *
     * @return
     */
    @ApiOperation(value = "获取banner，size为个数，默认3个.该接口给移动端使用")
    @RequestMapping(path = "/banner", method = {RequestMethod.GET})
    public Object banner(@RequestParam(name = "size", defaultValue = "10") Integer limit) {
        Integer tempLimit = limit;
        if (tempLimit.intValue() > maxBannerLimit) {
            tempLimit = maxBannerLimit;
        }
        return fetchBannerByTarget(LibraryConstants.BANNER_TARGET_MOBILE, tempLimit);
    }

    /**
     * 文库网页版banner
     *
     * @return
     */
    @ApiOperation(value = "文库网页版banner，size为个数，默认3个.该接口给 文库网页版 使用")
    @RequestMapping(path = "/lib/banner", method = {RequestMethod.GET})
    public Object banner4Library(@RequestParam(name = "size", defaultValue = "10") Integer limit) {
        Integer tempLimit = limit;
        if (tempLimit.intValue() > maxBannerLimit) {
            tempLimit = maxBannerLimit;
        }
        return fetchBannerByTarget(LibraryConstants.BANNER_TARGET_WEB_LIB, tempLimit);
    }

    /**
     * sui.me 网页版banner
     *
     * @return
     */
    @ApiOperation(value = "sui.me 网页版banner，size为个数，默认3个.该接口给 sui.me 网页版 使用")
    @RequestMapping(path = "/web/banner", method = {RequestMethod.GET})
    public Object banner4Web(@RequestParam(name = "size", defaultValue = "10") Integer limit) {
        Integer tempLimit = limit;
        if (tempLimit.intValue() > maxBannerLimit) {
            tempLimit = maxBannerLimit;
        }
        return fetchBannerByTarget(LibraryConstants.BANNER_TARGET_WEB_PRINT, tempLimit);
    }

    private Object fetchBannerByTarget(Integer target, Integer limit) {
        Integer tempTarget = target;
        if (tempTarget == null) {
            tempTarget = LibraryConstants.BANNER_TARGET_MOBILE;
        }
        CommonResultBean resultBean = new CommonResultBean();
        resultBean.setResult(Constants.NORMAL_RESULT_RIGHT);
        Sort sort = new Sort("id", OrderType.DESC);
        Conds conds = new Conds();
        conds.equal("actived", 1);
        conds.equal("target", tempTarget);
        List<BannerDto> list = this.bannerService.fetchDtoSearchByPage(conds, sort, 1, limit);
        resultBean.setBody(list);
        return resultBean;
    }
}

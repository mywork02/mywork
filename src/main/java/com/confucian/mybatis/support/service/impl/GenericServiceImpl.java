package com.confucian.mybatis.support.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.mapper.GenericMapper;
import com.confucian.mybatis.support.service.GenericService;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericServiceImpl<T> implements GenericService<T> {

    /**
     * logger
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected abstract GenericMapper<T> getGenericMapper();

    @Override
    public boolean save(T t) {
        return getGenericMapper().save(t) == 1;
    }

    @Override
    public boolean update(T t) {
        return getGenericMapper().update(t) == 1;
    }

    @Override
    public T fetchById(Serializable id) {
        return getGenericMapper().fetchById(id);
    }

    @Override
    public T fetchSearchByConds(Conds conds) {
        List<T> list = fetchSearchByPage(conds, null, 0, 1);
        return null == list || list.size() == 0 ? null : list.get(0);
    }

    @Override
    public boolean existByConds(Conds conds) {
        List<T> list = fetchSearchByPage(conds, null, 0, 1);
        return null != list && list.size() > 0;
    }

    @Override
    public List<T> fetchSearchByPage(Conds conds, Sort sort, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
        params.put("limit", pageSize > 0 ? pageSize : 0);
        params.put("sort", sort);
        return getGenericMapper().fetchSearchByPage(params);
    }

    @Override
    public int count(Conds conds) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        return getGenericMapper().count(params);
    }

    @Override
    public Page fetchPageSearch(Conds conds, Sort sort, int page, int pageSize) {
        int count = this.count(conds);
        List<T> list = this.fetchSearchByPage(conds, sort, page, pageSize);
        return generatePage(page, pageSize, count, list);
    }

    @Override
    public boolean delete(Serializable id) {
        T t = fetchById(id);
        if (null == t) {
            return false;
        }
        return getGenericMapper().delete(id) == 1;
    }

    /**
     * 生成page对象,并且初始化页数
     *
     * @param page      当前页数
     * @param pageSize  每页显示条数
     * @param totalSize 总记录数
     * @return page
     */
    protected Page generatePage(int page, int pageSize, int totalSize, List<?> data) {
        int start = 0;
        if (page > 1) {
            start = (page - 1) * pageSize;
        }
        return new Page(start, totalSize, pageSize, data);
    }

    /**
     * 生成mapper需要的参数
     *
     * @param conds    条件集合
     * @param sort     排序
     * @param page     当前页
     * @param pageSize 每页显示条数
     * @return params
     */
    protected Map<String, Object> generateParamsMap(Conds conds, Sort sort, int page, int pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("conds", conds);
        params.put("offset", page > 0 ? ((page - 1) * pageSize) : 0);
        params.put("limit", pageSize > 0 ? pageSize : 0);
        params.put("sort", sort);
        return params;
    }
}

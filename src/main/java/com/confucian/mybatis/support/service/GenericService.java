package com.confucian.mybatis.support.service;

import java.io.Serializable;
import java.util.List;

import com.confucian.framework.support.Page;
import com.confucian.mybatis.support.sql.Conds;
import com.confucian.mybatis.support.sql.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface GenericService<T> {

    /**
     * 保存对象
     *
     * @param t
     * @return
     */
    boolean save(T t);

    /**
     * 更新对象
     *
     * @param t
     * @return
     */
    boolean update(T t);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    T fetchById(Serializable id);

    /**
     * 根据条件查询
     *
     * @param conds
     * @return
     */
    T fetchSearchByConds(Conds conds);

    /**
     * 根据查询条件判断是否存在(true 存在 | false 不存在)
     *
     * @param conds
     * @return true|false
     */
    boolean existByConds(Conds conds);

    /**
     * 分页查询
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return
     */
    List<T> fetchSearchByPage(Conds conds, Sort sort, int page, int pageSize);

    /**
     * 分页查询,返回封装好的page
     *
     * @param conds    条件
     * @param sort     排序
     * @param page     起始条数
     * @param pageSize 分页大小
     * @return page
     */
    Page fetchPageSearch(Conds conds, Sort sort, int page, int pageSize);

    /**
     * 查询总条数
     *
     * @param conds 条件
     * @return 条数
     */
    int count(Conds conds);

    /**
     * 根据删除对象
     *
     * @param id
     * @return
     */
    boolean delete(Serializable id);
}

package com.confucian.mybatis.support.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericMapper<T> {

    /**
     * 保存对象
     *
     * @param t
     * @return
     */
    Long save(T t);

    /**
     * 更新对象
     *
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 根据Id查询对象
     *
     * @param id
     * @return
     */
    T fetchById(Serializable id);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    List<T> fetchSearchByPage(Map<String, Object> params);

    /**
     * 查询总数
     *
     * @return
     */
    int count(Map<String, Object> params);

    /**
     * 删除对象
     *
     * @param id
     * @return
     */
    int delete(Serializable id);
}

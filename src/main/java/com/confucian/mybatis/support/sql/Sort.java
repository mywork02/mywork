package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.OrderType;

/**
 * @Description: 排序
 * @author: locker
 * @date: 2013-7-10 下午4:44:14
 * @version: V1.0
 */
public class Sort {
    private String param;
    private OrderType type;

    public Sort(String param, OrderType type) {
        this.param = param;
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public OrderType getType() {
        return type;
    }
}

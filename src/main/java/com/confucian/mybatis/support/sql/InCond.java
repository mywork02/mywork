package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 等于
 * @author: edward
 * @date: 2013-7-11 上午11:56:01
 * @version: V1.0
 */
public class InCond extends Cond {
    private String param;
    private Object[] value;

    public InCond(LinkType linkType, String param, Object[] value) {
        super(CondType.IN, linkType);
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public Object getValue() {
        return value;
    }
}

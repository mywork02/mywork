package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 小于
 * @author: edward
 * @date: 2013-7-11 下午1:17:56
 * @version: V1.0
 */
public class LessThanCond extends Cond {

    private String param;
    private Object value;

    public LessThanCond(LinkType linkType, String param, Object value) {
        super(CondType.LESSTHAN, linkType);
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

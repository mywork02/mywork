package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 小于等于
 * @author: edward
 * @date: 2013-7-11 下午1:35:28
 * @version: V1.0
 */
public class LessEqualCond extends Cond {
    private String param;
    private Object value;

    public LessEqualCond(LinkType linkType, String param, Object value) {
        super(CondType.LESSEQUAL, linkType);
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

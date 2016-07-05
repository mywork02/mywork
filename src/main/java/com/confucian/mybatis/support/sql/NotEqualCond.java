package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

public class NotEqualCond extends Cond {
    private String param;
    private Object value;

    public NotEqualCond(LinkType linkType, String param, Object value) {
        super(CondType.NOTEQUAL, linkType);
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

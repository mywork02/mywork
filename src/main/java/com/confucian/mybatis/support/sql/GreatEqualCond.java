package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 大于等于
 * @author: edward
 * @date: 2013-7-11 下午1:18:25
 * @version: V1.0
 */
public class GreatEqualCond extends Cond {

    private String param;
    private Object value;

    public GreatEqualCond(LinkType linkType, String param, Object value) {
        super(CondType.GREATEQUAL, linkType);
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

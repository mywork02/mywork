package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 之间
 * @author: edward
 * @date: 2013-7-11 下午1:25:14
 * @version: V1.0
 */
public class BetweenCond extends Cond {

    private String param;
    private Object startValue;
    private Object endValue;

    public BetweenCond(LinkType linkType, String param, Object startValue, Object endValue) {
        super(CondType.BETWEEN, linkType);
        this.param = param;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public String getParam() {
        return param;
    }

    public Object getStartValue() {
        return startValue;
    }

    public Object getEndValue() {
        return endValue;
    }

}

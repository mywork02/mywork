package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 空
 * @author: edward
 * @date: 2013-7-11 上午11:55:41
 * @version: V1.0
 */
public class IsNullCond extends Cond {

    private String param;

    public IsNullCond(LinkType linkType, String param) {
        super(CondType.ISNULL, linkType);
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}

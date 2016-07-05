package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 非空
 * @author: edward
 * @date: 2013-7-11 下午1:34:51
 * @version: V1.0
 */
public class NotNullCond extends Cond {

    private String param;

    public NotNullCond(LinkType linkType, String param) {
        super(CondType.NOTNULL, linkType);
        this.param = param;
    }

    public String getParam() {
        return param;
    }
}

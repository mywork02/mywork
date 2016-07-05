package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 条件
 * @author: edward
 * @date: 2013-7-11 下午1:20:00
 * @version: V1.0
 */
public class Cond {

    private CondType condType;
    private LinkType linkType;

    public Cond(CondType condType, LinkType linkType) {
        this.condType = condType;
        this.linkType = linkType;
    }

    public String getCondType() {
        return condType.name();
    }

    public String getLinkType() {
        return linkType.name();
    }
}

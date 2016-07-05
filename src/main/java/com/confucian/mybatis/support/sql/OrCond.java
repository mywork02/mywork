package com.confucian.mybatis.support.sql;

import java.util.List;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LinkType;

public class OrCond extends Cond {
    private String param;
    private List<Cond> value;

    public OrCond(LinkType linkType, String param, List<Cond> value) {
        super(CondType.OR, linkType);
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public List<Cond> getValue() {
        return value;
    }
}

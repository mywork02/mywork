package com.confucian.mybatis.support.sql;

import com.confucian.mybatis.support.scope.CondType;
import com.confucian.mybatis.support.scope.LikeType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 模糊
 * @author: edward
 * @date: 2013-7-11 上午11:56:19
 * @version: V1.0
 */
public class LikeCond extends Cond {

    private String param;
    private String value;

    public LikeCond(LinkType linkType, String param, LikeType likeType, String value) {
        super(CondType.LIKE, linkType);
        this.param = param;
        switch (likeType) {
        case LEFT:
            this.value = new StringBuilder("%").append(value).toString();
            break;
        case RIGHT:
            this.value = new StringBuilder(value).append("%").toString();
            break;
        case NORMAL:
            this.value = new StringBuilder("%").append(value).append("%").toString();
            break;
        default:
            this.value = value;
            break;
        }
    }

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }
}

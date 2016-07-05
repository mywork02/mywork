package com.confucian.mybatis.support.sql;

import java.util.ArrayList;
import java.util.List;

import com.confucian.mybatis.support.scope.LikeType;
import com.confucian.mybatis.support.scope.LinkType;

/**
 * @Description: 条件集合
 * @author: edward
 * @date: 2013-7-11 上午11:48:36
 * @version: V1.0
 */
public class Conds {
    List<Cond> conds = new ArrayList<Cond>();

    /**
     * and连接 等于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds equal(String param, Object value) {
        if (null == value) return this;
        conds.add(new EqualCond(LinkType.AND, param, value));
        return this;
    }

    /**
     * and连接 不等于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds notEqual(String param, Object value) {
        if (null == value) return this;
        conds.add(new NotEqualCond(LinkType.AND, param, value));
        return this;
    }
    
    /**
     * or连接 等于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds equalOr(String param, Object value) {
        if (null == value) return this;
        conds.add(new EqualCond(LinkType.OR, param, value));
        return this;
    }

    /**
     * and连接 大于等于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds greatEqual(String param, Object value) {
        if (null == value) return this;
        conds.add(new GreatEqualCond(LinkType.AND, param, value));
        return this;
    }

    /**
     * or连接 大于等于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds greatEqualOr(String param, Object value) {
        if (null == value) return this;
        conds.add(new GreatEqualCond(LinkType.OR, param, value));
        return this;
    }

    /**
     * and连接 大于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds greatThan(String param, Object value) {
        if (null == value) return this;
        conds.add(new GreatThanCond(LinkType.AND, param, value));
        return this;
    }

    /**
     * or连接 大于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds greatThanOr(String param, Object value) {
        if (null == value) return this;
        conds.add(new GreatThanCond(LinkType.OR, param, value));
        return this;
    }

    /**
     * and连接 小于等于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds lessEqual(String param, Object value) {
        if (null == value) return this;
        conds.add(new LessEqualCond(LinkType.AND, param, value));
        return this;
    }

    /**
     * or连接 小于等于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds lessEqualOr(String param, Object value) {
        if (null == value) return this;
        conds.add(new LessEqualCond(LinkType.OR, param, value));
        return this;
    }

    /**
     * and连接 小于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds lessThan(String param, Object value) {
        if (null == value) return this;
        conds.add(new LessThanCond(LinkType.AND, param, value));
        return this;
    }

    /**
     * or连接 小于
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds lessThanOr(String param, Object value) {
        if (null == value) return this;
        conds.add(new LessThanCond(LinkType.OR, param, value));
        return this;
    }

    /**
     * and连接 之间
     * 
     * @param param 字段
     * @param startValue 开始值
     * @param endValue 结束值
     * @return 条件集
     */
    public Conds between(String param, Object startValue, Object endValue) {
        if (null != startValue && null != endValue) {
            conds.add(new BetweenCond(LinkType.AND, param, startValue, endValue));
        } else if (null != startValue && null == endValue) {
            conds.add(new GreatEqualCond(LinkType.AND, param, startValue));
        } else if (null == startValue && null != endValue) {
            conds.add(new LessEqualCond(LinkType.AND, param, endValue));
        }
        return this;
    }

    /**
     * or连接 之间
     * 
     * @param param 字段
     * @param startValue 开始值
     * @param endValue 结束值
     * @return 条件集
     */
    public Conds betweenOr(String param, Object startValue, Object endValue) {
        if (null != startValue && null != endValue) {
            conds.add(new BetweenCond(LinkType.OR, param, startValue, endValue));
        } else if (null != startValue && null == endValue) {
            conds.add(new GreatEqualCond(LinkType.OR, param, startValue));
        } else if (null == startValue && null != endValue) {
            conds.add(new LessEqualCond(LinkType.OR, param, endValue));
        }
        return this;
    }

    /**
     * and连接 空
     * 
     * @param param 字段
     * @return 条件集
     */
    public Conds isNull(String param) {
        conds.add(new IsNullCond(LinkType.AND, param));
        return this;
    }

    /**
     * or连接 空
     * 
     * @param param 字段
     * @return 条件集
     */
    public Conds isNullOr(String param) {
        conds.add(new IsNullCond(LinkType.OR, param));
        return this;
    }

    /**
     * and连接 空
     * 
     * @param param 字段
     * @return 条件集
     */
    public Conds notNull(String param) {
        conds.add(new NotNullCond(LinkType.AND, param));
        return this;
    }

    /**
     * or连接 空
     * 
     * @param param 字段
     * @return 条件集
     */
    public Conds notNullOr(String param) {
        conds.add(new NotNullCond(LinkType.OR, param));
        return this;
    }

    /**
     * and连接 模糊
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds like(String param, String value) {
        if (null == value) return this;
        conds.add(new LikeCond(LinkType.AND, param, LikeType.NORMAL, value));
        return this;
    }

    /**
     * or连接 模糊
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds likeOr(String param, String value) {
        if (null == value) return this;
        conds.add(new LikeCond(LinkType.OR, param, LikeType.NORMAL, value));
        return this;
    }

    /**
     * and连接 左查询
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds leftLike(String param, String value) {
        if (null == value) return this;
        conds.add(new LikeCond(LinkType.AND, param, LikeType.LEFT, value));
        return this;
    }

    /**
     * or连接 左查询
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds leftLikeOr(String param, String value) {
        if (null == value) return this;
        conds.add(new LikeCond(LinkType.OR, param, LikeType.LEFT, value));
        return this;
    }

    /**
     * and连接 右查询
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds rightLike(String param, String value) {
        if (null == value) return this;
        conds.add(new LikeCond(LinkType.AND, param, LikeType.RIGHT, value));
        return this;
    }

    /**
     * or连接 右查询
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds rightLikeOr(String param, String value) {
        if (null == value) return this;
        conds.add(new LikeCond(LinkType.OR, param, LikeType.RIGHT, value));
        return this;
    }

    /**
     * and连接 包含
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds in(String param, Object[] value) {
        if (null == value) return this;
        conds.add(1 == value.length ? new EqualCond(LinkType.AND, param, value[0]) : new InCond(LinkType.AND,
                param, value));
        return this;
    }

    /**
     * or连接 包含
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds inOr(String param, Object[] value) {
        if (null == value) return this;
        conds.add(1 == value.length ? new EqualCond(LinkType.OR, param, value[0]) : new InCond(LinkType.OR, param,
                value));
        return this;
    }

    /**
     * and连接 多个or组合
     * 
     * @param param 字段
     * @param value 值
     * @return 条件集
     */
    public Conds orCond(String param, List<Cond> value) {
        if (null == value)
            return this;
        conds.add(new OrCond(LinkType.AND, param, value));
        return this;
    }

    public List<Cond> getConds() {
        return conds;
    }
}

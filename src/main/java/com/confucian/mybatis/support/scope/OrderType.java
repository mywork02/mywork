package com.confucian.mybatis.support.scope;

public enum OrderType {

    DESC, ASC;

    public static OrderType ordinalByName(String name) {
        if (null == name) return null;
        OrderType[] types = values();
        for (int i = 0, len = types.length; i < len; i++) {
            OrderType type = types[i];
            if (type.name().equalsIgnoreCase(name)) { return type; }
        }
        return null;
    }
}

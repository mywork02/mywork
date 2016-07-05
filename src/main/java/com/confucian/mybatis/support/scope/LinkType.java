package com.confucian.mybatis.support.scope;

public enum LinkType {

    AND, OR;

    public static LinkType ordinalByName(String name) {
        if (null == name) return null;
        LinkType[] types = values();
        for (int i = 0, len = types.length; i < len; i++) {
            LinkType type = types[i];
            if (type.name().equalsIgnoreCase(name)) { return type; }
        }
        return null;
    }
}

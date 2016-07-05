package com.confucian.mybatis.support.scope;

public enum LikeType {

    RIGHT, LEFT, NORMAL;

    public static LikeType ordinalByName(String name) {
        if (null == name) return null;
        LikeType[] types = values();
        for (int i = 0, len = types.length; i < len; i++) {
            LikeType type = types[i];
            if (type.name().equalsIgnoreCase(name)) { return type; }
        }
        return null;
    }
}

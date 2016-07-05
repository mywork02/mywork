package com.confucian.mybatis.support.scope;

public enum CondType {

    EQUAL, GREATEQUAL, GREATTHAN, LESSEQUAL, LESSTHAN, BETWEEN, ISNULL, NOTNULL, LIKE, IN, OR, NOTEQUAL;

    public static CondType ordinalByName(String name) {
        if (null == name) return null;
        CondType[] types = values();
        for (int i = 0, len = types.length; i < len; i++) {
            CondType type = types[i];
            if (type.name().equalsIgnoreCase(name)) { return type; }
        }
        return null;
    }
}

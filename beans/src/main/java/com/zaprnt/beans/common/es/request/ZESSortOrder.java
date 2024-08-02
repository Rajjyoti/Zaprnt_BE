package com.zaprnt.beans.common.es.request;

public enum ZESSortOrder {
    ASC("asc"),
    DESC("desc");

    private final String value;

    ZESSortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

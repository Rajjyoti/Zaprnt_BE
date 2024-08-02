package com.zaprnt.beans.common.es.request;

public enum ZESSortOrder {
    ASC("Asc"),
    DESC("Desc");

    private final String value;

    ZESSortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

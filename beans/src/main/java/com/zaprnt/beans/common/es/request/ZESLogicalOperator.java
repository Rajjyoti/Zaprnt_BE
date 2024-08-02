package com.zaprnt.beans.common.es.request;

public enum ZESLogicalOperator {
    AND("must"),
    OR("should");

    private final String value;

    ZESLogicalOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


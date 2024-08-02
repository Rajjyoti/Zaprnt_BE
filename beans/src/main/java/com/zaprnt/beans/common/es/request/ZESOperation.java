package com.zaprnt.beans.common.es.request;

public enum ZESOperation {
    EQUALS("term"),
    GREATER_THAN("gt"),
    LESS_THAN("lt"),
    GREATER_THAN_OR_EQUAL("gte"),
    LESS_THAN_OR_EQUAL("lte"),
    RANGE("range"),
    EXISTS("exists"),
    NOT_EXISTS("not_exists");

    private final String value;

    ZESOperation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


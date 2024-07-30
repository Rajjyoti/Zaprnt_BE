package com.zaprnt.beans.error;

public enum ZError {
    CART_ITEM_NOT_FOUND("101", "cart.item.not.found"),
    ORDER_NOT_FOUND("102", "order.not.found"),
    ORDERS_NOT_FOUND("103", "orders.not.found"),
    PRODUCT_NOT_FOUND("104", "product.not.found"),
    USER_NOT_FOUND("105", "user.not.found"),
    INCORRECT_PASSWORD("106", "incorrect.password"),
    NO_PRODUCTS_FOUND("107", "no.products.found"),
    CART_ITEMS_NOT_FOUND("108", "cart.items.not.found"),
    USER_ALREADY_EXISTS("109", "user.already.exists");

    private final String code;
    private final String key;

    ZError(String code, String key) {
        this.code = code;
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

}

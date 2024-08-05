package com.zaprnt.beans.kafka;

public class ProductUpdateEvent {
    public ProductUpdateEvent() {}

    public ProductUpdateEvent(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    private String productId;
    private int quantity; //quantity to be subtracted

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

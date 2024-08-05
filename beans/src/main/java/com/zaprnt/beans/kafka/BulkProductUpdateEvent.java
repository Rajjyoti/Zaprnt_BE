package com.zaprnt.beans.kafka;

import java.util.List;

public class BulkProductUpdateEvent {
    private List<ProductUpdateEvent> productDetails;

    public BulkProductUpdateEvent() {
    }

    public BulkProductUpdateEvent(List<ProductUpdateEvent> productDetails) {
        this.productDetails = productDetails;
    }

    public List<ProductUpdateEvent> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<ProductUpdateEvent> productDetails) {
        this.productDetails = productDetails;
    }
}

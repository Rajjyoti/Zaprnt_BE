package com.zaprent.utils;

import com.zaprnt.beans.dtos.request.product.ProductCreateRequest;
import com.zaprnt.beans.dtos.request.product.ProductUpdateRequest;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.enums.ProductStatus;
import com.zaprnt.beans.models.Product;

import java.util.ArrayList;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotBlank;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class ProductUtils {

    public static List<ProductResponse> convertToProductResponses(List<Product> products) {
        if (isNull(products) || products.isEmpty()) {
            return null;
        }
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product: products) {
            if (isNull(product)) {
                continue;
            }
            ProductResponse response = convertToProductResponse(product);
            if (nonNull(response)) {
                responses.add(response);
            }
        }
        return responses;
    }

    public static ProductResponse convertToProductResponse(Product product) {
        if (isNull(product)) {
            return null;
        }
        return new ProductResponse()
                .setId(product.getId())
                .setTitle(product.getTitle())
                .setCategory(product.getCategory())
                .setSubCategory(product.getSubCategory())
                .setAttributes(product.getAttributes())
                .setCondition(product.getCondition())
                .setImages(product.getImages())
                .setLocation(product.getLocation())
                .setOwnerId(product.getOwnerId())
                .setOwnerName(product.getOwnerName())
                .setPrice(product.getPrice())
                .setQuantity(product.getQuantity())
                .setStatus(product.getStatus())
                .setDescription(product.getDescription());
    }

    public static Product convertToProduct(ProductCreateRequest request) {
        if (isNull(request)) {
            return null;
        }
        return new Product()
                .setTitle(request.getTitle())
                .setCategory(request.getCategory())
                .setSubCategory(request.getSubCategory())
                .setAttributes(request.getAttributes())
                .setCondition(request.getCondition())
                .setImages(request.getImages())
                .setLocation(request.getLocation())
                .setOwnerId(request.getOwnerId())
                .setOwnerName(request.getOwnerName())
                .setPrice(request.getPrice())
                .setQuantity(request.getQuantity())
                .setStatus(ProductStatus.AVAILABLE)
                .setDescription(request.getDescription());
    }

    public static Product getUpdatedProduct(Product product, ProductUpdateRequest request) {
        if (isNull(request)) {
            return null;
        }
        if (isNotBlank(request.getOwnerName())) {
            product.setOwnerName(request.getOwnerName());
        }
        if (isNotBlank(request.getTitle())) {
            product.setTitle(request.getTitle());
        }
        if (isNotBlank(request.getDescription())) {
            product.setDescription(request.getDescription());
        }
        if (isNotBlank(request.getLocation())) {
            product.setLocation(request.getLocation());
        }
        if (nonNull(request.getStatus())) {
            product.setStatus(request.getStatus());
        }
        if (nonNull(request.getPrice())) {
            product.setPrice(request.getPrice());
        }
        if (isNotBlank(request.getCondition())) {
            product.setCondition(request.getCondition());
        }
        if (nonNull(request.getQuantity())) {
            product.setQuantity(request.getQuantity());
            if (request.getQuantity() == 0) {
                product.setStatus(ProductStatus.OUT_OF_STOCK);
            }
        }
        product.setImages(request.getImages());
        return product;
    }
}

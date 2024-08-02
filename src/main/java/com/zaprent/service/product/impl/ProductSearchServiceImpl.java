package com.zaprent.service.product.impl;

import com.zaprent.config.ZElasticSearchClient;
import com.zaprent.service.product.IProductSearchService;
import com.zaprnt.beans.common.es.request.ZESRequest;
import com.zaprnt.beans.common.es.response.ZESResponse;
import com.zaprnt.beans.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductSearchServiceImpl implements IProductSearchService {
    @Autowired
    private ZElasticSearchClient elasticsearchClient;

    private static final String PRODUCT_INDEX = "product";

    @Override
    public ZESResponse<Product> searchProducts(ZESRequest esRequest) {
        try {
            return elasticsearchClient.search(esRequest, PRODUCT_INDEX, Product.class);
        } catch (Exception e) {
            log.error("[ERROR][searchProducts] error in search", e);
            return new ZESResponse<>();
        }
    }

    @Override
    public void saveProductInEs(Product product) {
        try {
            elasticsearchClient.save(PRODUCT_INDEX, product.getId(), product);
        } catch (Exception e) {
            log.error("[ERROR][saveProductInEs] error in save", e);
        }
    }

    @Override
    public void saveProductsInEs(List<Product> products) {
        try {
            elasticsearchClient.bulkSave(PRODUCT_INDEX, products);
        } catch (Exception e) {
            log.error("[ERROR][saveProductInEs] error in save bulk", e);
        }
    }

    @Override
    public void deleteProductFromEs(String id) {
        try {
            elasticsearchClient.delete(PRODUCT_INDEX, id);
        } catch (Exception e) {
            log.error("[ERROR][deleteProductFromEs] error in delete", e);
        }
    }

    @Override
    public void deleteProductsFromEs(List<String> ids) {
        try {
            elasticsearchClient.deleteBulk(PRODUCT_INDEX, ids);
        } catch (Exception e) {
            log.error("[ERROR][deleteProductsFromEs] error in delete bulk", e);
        }
    }

    @Override
    public void deleteAllProductsFromEs() {
        try {
            elasticsearchClient.deleteAll(PRODUCT_INDEX);
        } catch (Exception e) {
            log.error("[ERROR][deleteAllProductsFromEs] error in delete all", e);
        }
    }
}

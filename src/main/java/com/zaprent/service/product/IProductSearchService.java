package com.zaprent.service.product;

import com.zaprnt.beans.common.es.request.ZESRequest;
import com.zaprnt.beans.common.es.response.ZESResponse;
import com.zaprnt.beans.models.Product;

import java.util.List;

public interface IProductSearchService {
    ZESResponse<Product> searchProducts(ZESRequest esRequest);

    void saveProductInEs(Product product);

    void saveProductsInEs(List<Product> products);

    void deleteProductFromEs(String id);

    void deleteProductsFromEs(List<String> ids);

    void deleteAllProductsFromEs();
}

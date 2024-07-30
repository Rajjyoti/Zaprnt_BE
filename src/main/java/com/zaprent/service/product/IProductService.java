package com.zaprent.service.product;

import com.zaprnt.beans.dtos.request.product.ProductCreateRequest;
import com.zaprnt.beans.dtos.request.product.ProductUpdateRequest;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.enums.Category;
import com.zaprnt.beans.models.Product;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductCreateRequest request);
    ProductResponse updateProduct(ProductUpdateRequest request);
    ProductResponse getProductById(String id);
    List<ProductResponse> getProductsByIds(List<String> productIds);
    ProductResponse getProductByOwnerId(String id);
    List<ProductResponse> getProductsByCategory(Category category);
    boolean deleteProduct(String id);
}

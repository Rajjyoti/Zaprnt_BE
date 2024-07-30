package com.zaprent.service.product.impl;

import com.zaprent.repo.product.IProductRepo;
import com.zaprent.service.product.IProductService;
import com.zaprnt.beans.dtos.request.product.ProductCreateRequest;
import com.zaprnt.beans.dtos.request.product.ProductUpdateRequest;
import com.zaprnt.beans.dtos.response.product.ProductResponse;
import com.zaprnt.beans.enums.Category;
import com.zaprnt.beans.error.ZError;
import com.zaprnt.beans.error.ZException;
import com.zaprnt.beans.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zaprent.utils.ProductUtils.*;
import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final IProductRepo productRepo;

    @Override
    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = productRepo.saveProduct(convertToProduct(request));
        return convertToProductResponse(product);
    }

    @Override
    public ProductResponse updateProduct(ProductUpdateRequest request) {
        Product product = productRepo.getProductById(request.getProductId());
        product = getUpdatedProduct(product, request);
        return convertToProductResponse(productRepo.saveProduct(product));
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepo.getProductById(id);
        if (isNull(product)) {
            throw new ZException(ZError.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND, id);
        }
        return convertToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getProductsByIds(List<String> productIds) {
        List<Product> products = productRepo.getProductsByIds(productIds);
        if (isNull(products) || products.isEmpty()) {
            throw new ZException(ZError.NO_PRODUCTS_FOUND);
        }
        return convertToProductResponses(products);
    }

    @Override
    public ProductResponse getProductByOwnerId(String id) {
        return convertToProductResponse(productRepo.getProductByOwnerId(id));
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Category category) {
        return convertToProductResponses(productRepo.getProductsByCategory(category));
    }

    @Override
    public boolean deleteProduct(String id) {
        return productRepo.deleteProductById(id);
    }
}

package com.zaprent.repo.product;

import com.zaprnt.beans.enums.Category;
import com.zaprnt.beans.models.Product;

import java.util.List;

public interface CustomProductRepository {
    Product saveProduct(Product product);
    Product getProductById(String id);
    List<Product> getProductsByIds(List<String> productIds);
    Product getProductByOwnerId(String id);
    List<Product> getProductsByCategory(Category category);
    boolean deleteProductById(String id);
}

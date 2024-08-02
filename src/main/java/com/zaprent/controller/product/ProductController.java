package com.zaprent.controller.product;

import com.zaprent.service.product.IProductSearchService;
import com.zaprent.service.product.IProductService;
import com.zaprnt.beans.common.es.request.ZESRequest;
import com.zaprnt.beans.dtos.request.product.ProductCreateRequest;
import com.zaprnt.beans.dtos.request.product.ProductUpdateRequest;
import com.zaprnt.beans.enums.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final IProductSearchService productSearchService;

    @GetMapping("/ids/{id}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable String id) {
        return okResponseEntity(productService.getProductById(id));
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<Map<String, Object>> getProductsByCategory(@PathVariable Category category) {
        return okResponseEntity(productService.getProductsByCategory(category));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return okCreatedResponseEntity(productService.createProduct(request));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> searchProducts(@Valid @RequestBody ZESRequest request) {
        return okCreatedResponseEntity(productSearchService.searchProducts(request));
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProduct(@Valid @RequestBody ProductUpdateRequest request) {
        return okResponseEntity(productService.updateProduct(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable String id) {
        return okResponseEntity(productService.deleteProduct(id));
    }
}

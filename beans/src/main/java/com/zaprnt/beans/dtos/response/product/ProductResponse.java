package com.zaprnt.beans.dtos.response.product;

import com.zaprnt.beans.dtos.ProductAttribute;
import com.zaprnt.beans.enums.Category;
import com.zaprnt.beans.enums.ProductStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductResponse {
    private String id;
    private String ownerId;  // Store owner ID as a string, or use a reference to another document
    private String ownerName;
    private String title;
    private String description;
    private Category category;
    private Category subCategory;
    private List<ProductAttribute> attributes;
    private List<String> images;
    private String location;
    private ProductStatus status;
    private BigDecimal price;
    private int quantity;
    private String condition;
}

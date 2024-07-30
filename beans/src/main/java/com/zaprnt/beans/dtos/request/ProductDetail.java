package com.zaprnt.beans.dtos.request;

import com.zaprnt.beans.dtos.ProductAttribute;
import com.zaprnt.beans.enums.Category;
import com.zaprnt.beans.enums.ProductStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductDetail {
    private String title;
    private String description;
    private Category category;
    private Category subCategory;
    private List<ProductAttribute> attributes;
    private List<String> images;
    private ProductStatus status;
    private BigDecimal price;
    private String condition;
}

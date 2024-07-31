package com.zaprnt.beans.models;

import com.zaprnt.beans.common.BaseMongoBean;
import com.zaprnt.beans.dtos.ProductAttribute;
import com.zaprnt.beans.enums.Category;
import com.zaprnt.beans.enums.ProductStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@Document
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseMongoBean {
    @Indexed
    private String ownerId;  // Store owner ID as a string, or use a reference to another document
    private String ownerName;
    private String title;
    private String description;
    @Indexed
    private Category category;
    private Category subCategory;
    private List<ProductAttribute> attributes;
    private List<String> images;
    private String location;
    private ProductStatus status;
    private int quantity;
    private BigDecimal price;
    private String condition;
}

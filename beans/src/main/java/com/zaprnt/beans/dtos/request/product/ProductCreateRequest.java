package com.zaprnt.beans.dtos.request.product;

import com.zaprnt.beans.dtos.ProductAttribute;
import com.zaprnt.beans.enums.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductCreateRequest {
    @NotBlank
    private String ownerId;  // Store owner ID as a string, or use a reference to another document
    @NotBlank
    private String ownerName;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private Category category;
    @NotBlank
    private Category subCategory;
    @NotEmpty
    @Valid
    private List<ProductAttribute> attributes;
    @NotBlank
    private List<String> images;
    @NotBlank
    private String location;
    @NotBlank
    private BigDecimal price;
    private int quantity;
    private String condition;
}

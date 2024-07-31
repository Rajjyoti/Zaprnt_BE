package com.zaprnt.beans.dtos.request.product;

import com.zaprnt.beans.enums.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductUpdateRequest {
    @NotBlank
    private String productId;
    private String ownerName;
    private String title;
    private String description;
    private List<String> images;
    private String location;
    private ProductStatus status;
    private BigDecimal price;
    private Integer quantity;
    private String condition;
}

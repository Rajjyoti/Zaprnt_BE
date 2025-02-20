package com.zaprnt.beans.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductAttribute {
    @NotBlank
    private String key;
    @NotNull
    private Object value;
}

package com.zaprnt.beans.dtos.request.wishList;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WishListRequest {
    @NotBlank
    private String productId;
    @NotBlank
    private String userId;
}

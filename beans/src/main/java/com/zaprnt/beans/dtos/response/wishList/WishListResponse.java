package com.zaprnt.beans.dtos.response.wishList;

import com.zaprnt.beans.dtos.request.ProductDetail;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WishListResponse {
    private String id;
    private String productId;
    private String userId;
    private ProductDetail productDetail;
}

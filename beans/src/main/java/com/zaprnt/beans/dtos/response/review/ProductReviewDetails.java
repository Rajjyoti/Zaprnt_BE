package com.zaprnt.beans.dtos.response.review;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProductReviewDetails {
    private String productId;
    List<ReviewResponse> reviewResponses;
    private BigDecimal rating;
    private int ratingCount;
}

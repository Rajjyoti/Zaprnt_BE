package com.zaprnt.beans.dtos.request.review;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReviewRequest {
    private String productId;
    private String userId;
    private int rating;
    private String title;
    private String comment;
}

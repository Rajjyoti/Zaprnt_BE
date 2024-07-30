package com.zaprent.service.review;

import com.zaprnt.beans.dtos.request.review.ReviewRequest;
import com.zaprnt.beans.dtos.response.review.ProductReviewDetails;
import com.zaprnt.beans.models.Review;


public interface IReviewService {
    ProductReviewDetails getReviewsByProductId(String id);
    Review saveReview(ReviewRequest request);
}

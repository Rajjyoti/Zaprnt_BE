package com.zaprent.repo.review;

import com.zaprnt.beans.models.Review;

import java.math.BigDecimal;
import java.util.List;

public interface CustomReviewRepository {
    Review saveReview(Review review);
    List<Review> getReviewsByProductId(String id);
    BigDecimal getAverageRatingForProduct(String productId);
}

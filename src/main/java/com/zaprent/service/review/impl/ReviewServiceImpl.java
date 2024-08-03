package com.zaprent.service.review.impl;

import com.zaprent.repo.review.IReviewRepo;
import com.zaprent.service.review.IReviewService;
import com.zaprent.service.user.IUserService;
import com.zaprnt.beans.dtos.request.review.ReviewRequest;
import com.zaprnt.beans.dtos.response.review.ProductReviewDetails;
import com.zaprnt.beans.dtos.response.review.ReviewResponse;
import com.zaprnt.beans.models.Review;
import com.zaprnt.beans.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.zaprent.utils.ReviewUtils.convertToReview;
import static com.zaprent.utils.ReviewUtils.convertToReviewResponse;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final IReviewRepo reviewRepo;
    private final IUserService userService;

    @Override
    public Review saveReview(ReviewRequest request) {
        return reviewRepo.saveReview(convertToReview(request));
    }

    @Override
    public ProductReviewDetails getReviewsByProductId(String id) {
        List<Review> reviews = reviewRepo.getReviewsByProductId(id);
        BigDecimal averageRating = reviewRepo.getAverageRatingForProduct(id);
        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(this::mapToReviewResponse)
                .toList();
        return new ProductReviewDetails()
                .setReviewResponses(reviewResponses)
                .setRatingCount(reviews.size())
                .setProductId(id)
                .setRating(averageRating);
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        User user = userService.findUserById(review.getUserId());
        return convertToReviewResponse(review, user);
    }
}

package com.zaprent.utils;

import com.zaprnt.beans.dtos.request.review.ReviewRequest;
import com.zaprnt.beans.dtos.response.review.ReviewResponse;
import com.zaprnt.beans.models.Review;
import com.zaprnt.beans.models.User;

import static java.util.Objects.isNull;

public class ReviewUtils {
    public static Review convertToReview(ReviewRequest request) {
        if (isNull(request)) {
            return null;
        }
        return new Review()
                .setUserId(request.getUserId())
                .setComment(request.getComment())
                .setTitle(request.getTitle())
                .setRating(request.getRating())
                .setProductId(request.getProductId());
    }

    public static ReviewResponse convertToReviewResponse(Review review, User user) {
        if (isNull(review) || isNull(user)) {
            return null;
        }
        return new ReviewResponse()
                .setId(review.getId())
                .setUserId(review.getUserId())
                .setUserName(user.getUserName())
                .setUserProfilePicUrl(user.getProfilePicUrl())
                .setRating(review.getRating())
                .setComment(review.getComment())
                .setTitle(review.getTitle())
                .setCreatedTime(review.getCreatedTime())
                .setModifiedTime(review.getModifiedTime());
    }
}

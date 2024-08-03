package com.zaprent.service.review;

import com.zaprnt.beans.common.es.request.ZESRequest;
import com.zaprnt.beans.common.es.response.ZESResponse;
import com.zaprnt.beans.models.Review;

import java.util.List;

public interface IReviewSearchService {
    ZESResponse<Review> searchReviews(ZESRequest esRequest);

    void saveReviewInEs(Review review);

    void saveReviewsInEs(List<Review> reviews);

    void deleteReviewFromEs(String id);

    void deleteReviewsFromEs(List<String> ids);

    void deleteAllReviewsFromEs();
}

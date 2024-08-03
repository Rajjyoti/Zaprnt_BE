package com.zaprent.service.review.impl;

import com.zaprent.config.ZElasticSearchClient;
import com.zaprent.service.review.IReviewSearchService;
import com.zaprnt.beans.common.es.request.ZESRequest;
import com.zaprnt.beans.common.es.response.ZESResponse;
import com.zaprnt.beans.models.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReviewSearchServiceImpl implements IReviewSearchService {

    @Autowired
    private ZElasticSearchClient elasticsearchClient;

    private static final String REVIEW_INDEX = "review";

    @Override
    public ZESResponse<Review> searchReviews(ZESRequest esRequest) {
        try {
            return elasticsearchClient.search(esRequest, REVIEW_INDEX, Review.class);
        } catch (Exception e) {
            log.error("[ERROR][searchReviews] error in search", e);
            return new ZESResponse<>();
        }
    }

    @Override
    public void saveReviewInEs(Review review) {
        try {
            elasticsearchClient.save(REVIEW_INDEX, review.getId(), review);
        } catch (Exception e) {
            log.error("[ERROR][saveReviewInEs] error in save", e);
        }
    }

    @Override
    public void saveReviewsInEs(List<Review> reviews) {
        try {
            elasticsearchClient.bulkSave(REVIEW_INDEX, reviews);
        } catch (Exception e) {
            log.error("[ERROR][saveReviewsInEs] error in save bulk", e);
        }
    }

    @Override
    public void deleteReviewFromEs(String id) {
        try {
            elasticsearchClient.delete(REVIEW_INDEX, id);
        } catch (Exception e) {
            log.error("[ERROR][deleteReviewFromEs] error in delete", e);
        }
    }

    @Override
    public void deleteReviewsFromEs(List<String> ids) {
        try {
            elasticsearchClient.deleteBulk(REVIEW_INDEX, ids);
        } catch (Exception e) {
            log.error("[ERROR][deleteReviewsFromEs] error in delete bulk", e);
        }
    }

    @Override
    public void deleteAllReviewsFromEs() {
        try {
            elasticsearchClient.deleteAll(REVIEW_INDEX);
        } catch (Exception e) {
            log.error("[ERROR][deleteAllReviewsFromEs] error in delete all", e);
        }
    }
}

package com.zaprent.repo.review.impl;

import com.zaprent.repo.review.CustomReviewRepository;
import com.zaprnt.beans.models.Review;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Repository
public class IReviewRepoImpl implements CustomReviewRepository {
    private final MongoTemplate mongoTemplate;

    public IReviewRepoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Review saveReview(Review review) {
        return mongoTemplate.save(review);
    }

    @Override
    public List<Review> getReviewsByProductId(String id) {
        return mongoTemplate.find(new Query(Criteria.where("productId").is(id)), Review.class);
    }

    @Override
    public BigDecimal getAverageRatingForProduct(String productId) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("productId").is(productId)),
                Aggregation.group("productId")
                        .avg("rating").as("averageRating")
        );

        AggregationResults<Map> result = mongoTemplate.aggregate(aggregation, "reviews", Map.class);

        Map<String, Object> resultMap = result.getUniqueMappedResult();
        if (resultMap != null && resultMap.containsKey("averageRating")) {
            // Convert to BigDecimal
            double averageRating = (double) resultMap.get("averageRating");
            return new BigDecimal(averageRating).setScale(1, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        }
    }
}

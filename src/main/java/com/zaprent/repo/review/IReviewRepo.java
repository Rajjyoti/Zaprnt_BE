package com.zaprent.repo.review;

import com.zaprnt.beans.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepo extends MongoRepository<Review, String>, CustomReviewRepository {
}

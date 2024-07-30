package com.zaprent.controller.review;

import com.zaprent.service.review.IReviewService;
import com.zaprnt.beans.dtos.request.review.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.zaprnt.beans.error.ZResponseEntityBuilder.okCreatedResponseEntity;
import static com.zaprnt.beans.error.ZResponseEntityBuilder.okResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final IReviewService reviewService;

    @PostMapping("/product-ids/{productId}")
    public ResponseEntity<Map<String, Object>> getReviewsByProductId(@PathVariable String productId) {
        return okCreatedResponseEntity(reviewService.getReviewsByProductId(productId));
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveReview(@RequestBody ReviewRequest request) {
        return okResponseEntity(reviewService.saveReview(request));
    }
}

package org.example.reviewservice.review.controller;

import jakarta.validation.Valid;
import org.example.reviewservice.review.model.dto.CreateReviewRequest;
import org.example.reviewservice.review.model.dto.ReviewResponse;
import org.example.reviewservice.review.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;

    private ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponse createReview(@Valid @RequestBody CreateReviewRequest request) {
        return reviewService.createReview(request);
    }

    @GetMapping("/room/{id}")
    public List<ReviewResponse> getReviews(@PathVariable Long id) {
        return reviewService.getReviewsByRoomId(id);
    }

    @GetMapping("/{id}")
    public ReviewResponse getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id) {

        reviewService.deleteReview(id);
    }
}


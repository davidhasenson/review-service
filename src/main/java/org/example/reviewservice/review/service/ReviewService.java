package org.example.reviewservice.review.service;

import jakarta.transaction.Transactional;
import org.example.reviewservice.exceptions.NotFoundException;
import org.example.reviewservice.review.model.Review;
import org.example.reviewservice.review.model.dto.CreateReviewRequest;
import org.example.reviewservice.review.model.dto.ReviewResponse;
import org.example.reviewservice.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(
            ReviewRepository reviewRepository
    ) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request) {
        Review review = new Review();

        review.setRoomId(request.roomId());
        review.setReviewerName(request.reviewerName());
        review.setRating(request.rating());
        review.setReviewText(request.reviewText());
        review.setReviewDate(request.reviewDate());

        Review savedReview = reviewRepository.save(review);

        return convertToReviewResponse(savedReview);
    }


    public List<ReviewResponse> getReviewsByRoomId(Long roomId) {
        return reviewRepository.findByRoomId(roomId)
                .stream()
                .map(this::convertToReviewResponse)
                .toList();
    }

    public ReviewResponse getReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Recensionen med id " + id + " hittades inte."));
        return convertToReviewResponse(review);
    }


    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Recensionen med id " + id + " hittades inte"));
        reviewRepository.delete(review);
    }

    private ReviewResponse convertToReviewResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getReviewerName(),
                review.getRoomId(),
                review.getRating(),
                review.getReviewText(),
                review.getReviewDate()
        );
    }

}

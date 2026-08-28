package org.example.reviewservice.review.repository;

import org.example.reviewservice.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

 List<Review> findByRoomId(Long id);
}

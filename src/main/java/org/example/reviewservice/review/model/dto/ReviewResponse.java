package org.example.reviewservice.review.model.dto;

import java.time.LocalDate;

public record ReviewResponse(
        Long id,
        String reviewerName,
        Long roomId,
        int rating,
        String reviewText,
        LocalDate reviewDate
) {
}

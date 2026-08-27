package org.example.reviewservice.review.model.dto;

import java.time.LocalDate;

public record ReviewResponse(
        Long id,
        Long roomId,
        int rating,
        String reviewText,
        LocalDate reviewDate
) {
}

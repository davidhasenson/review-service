package org.example.reviewservice.review.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateReviewRequest(

        @NotBlank(message = "Room id is required")
        Long roomId,

        @NotBlank(message = "Betyg får inte vara tomt")
        @NotNull(message = "Det båste anges ett betyg")
        @Min(value = 1, message = "Minsta betyg är 1")
        @Max(value = 5, message = "Max betyg är 5")
        int rating,

        @NotBlank(message = "Recension får inte vara tomt")
        @NotNull(message = "Det båste anges en recension")
        String reviewText,

        @NotNull(message = "Date can not be null")
        LocalDate reviewDate

) {
}

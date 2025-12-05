package com.fitness.fitness_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCreateRequest {
    @NotBlank(message = "Activity type is required")
    @Size(max = 50, message = "Activity type must be at most 50 characters")
    private String type;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Activity date cannot be in the future")
    private LocalDate date;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer durationMinutes;

    @PositiveOrZero(message = "Distance must be 0 or greater")
    private Double distanceKm;

    @PositiveOrZero(message = "Calories must be 0 or greater")
    private Double caloriesBurned;

    @Size(max = 1000, message = "Notes can be at most 1000 characters")
    private String notes;
}

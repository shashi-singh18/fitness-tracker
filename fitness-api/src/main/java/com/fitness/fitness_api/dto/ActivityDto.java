package com.fitness.fitness_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    private Long id;
    private String type;
    private LocalDate date;
    private Integer durationMinutes;
    private Double distanceKm;
    private Double caloriesBurned;
    private String notes;
    private Long userId;
    private LocalDateTime createdAt;
}

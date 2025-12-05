package com.fitness.fitness_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklySummaryDto {
    private int totalActivities;
    private int totalDurationMinutes;
    private double totalCaloriesBurned;
}


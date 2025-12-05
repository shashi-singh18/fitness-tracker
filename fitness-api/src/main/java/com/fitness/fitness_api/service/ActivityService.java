package com.fitness.fitness_api.service;

import com.fitness.fitness_api.dto.WeeklySummaryDto;
import com.fitness.fitness_api.entity.Activity;
import java.util.List;
import java.util.Optional;

public interface ActivityService {
    Activity create(Activity activity);
    List<Activity> findByUserId(Long userId);
    Optional<Activity> findById(Long id);
    Activity update(Activity activity);
    void delete(Long id);
    WeeklySummaryDto getWeeklySummary(Long userId);
}

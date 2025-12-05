package com.fitness.fitness_api.service.impl;

import com.fitness.fitness_api.dto.WeeklySummaryDto;
import com.fitness.fitness_api.entity.Activity;
import com.fitness.fitness_api.entity.User;
import com.fitness.fitness_api.exception.ResourceNotFoundException;
import com.fitness.fitness_api.repository.ActivityRepository;
import com.fitness.fitness_api.repository.UserRepository;
import com.fitness.fitness_api.service.ActivityService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository,
                               UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Activity create(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return activityRepository.findByUser(user);
    }

    @Override
    public Optional<Activity> findById(Long id) {
        return activityRepository.findById(id);
    }

    @Override
    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public void delete(Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public WeeklySummaryDto getWeeklySummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.minusDays(6); // last 7 days

        List<Activity> activities = activityRepository.findByUserAndDateBetween(user, weekStart, today);

        int totalActivities = activities.size();
        int totalDuration = activities.stream()
                .mapToInt(a -> a.getDurationMinutes() == null ? 0 : a.getDurationMinutes())
                .sum();

        double totalCalories = activities.stream()
                .mapToDouble(a -> a.getCaloriesBurned() == null ? 0 : a.getCaloriesBurned())
                .sum();

        return new WeeklySummaryDto(totalActivities, totalDuration, totalCalories);
    }

}


package com.fitness.fitness_api.repository;

import com.fitness.fitness_api.entity.Activity;
import com.fitness.fitness_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUser(User user);
    List<Activity> findByUserAndDateBetween(User user, LocalDate start, LocalDate end);
}


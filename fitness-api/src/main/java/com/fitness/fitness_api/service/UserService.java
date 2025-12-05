package com.fitness.fitness_api.service;

import com.fitness.fitness_api.entity.User;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
}

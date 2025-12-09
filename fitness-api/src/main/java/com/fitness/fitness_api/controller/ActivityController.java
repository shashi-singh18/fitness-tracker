package com.fitness.fitness_api.controller;

import com.fitness.fitness_api.dto.ApiResponse;
import com.fitness.fitness_api.dto.ActivityCreateRequest;
import com.fitness.fitness_api.dto.ActivityDto;
import com.fitness.fitness_api.dto.ActivityUpdateRequest;
import com.fitness.fitness_api.entity.Activity;
import com.fitness.fitness_api.entity.User;
import com.fitness.fitness_api.exception.ResourceNotFoundException;
import com.fitness.fitness_api.service.ActivityService;
import com.fitness.fitness_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "http://localhost:4200")
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;

    public ActivityController(ActivityService activityService,
                              UserService userService) {
        this.activityService = activityService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ActivityDto>>> getByUser(
            @RequestParam Long userId,
            HttpServletRequest request
    ) {
        List<Activity> activities = activityService.findByUserId(userId);
        List<ActivityDto> dtos = activities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        ApiResponse<List<ActivityDto>> response = ApiResponse.success(
                "Activities fetched successfully",
                dtos,
                HttpStatus.OK.value(),
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ActivityDto>> create(
            @RequestParam Long userId,
            @Valid @RequestBody ActivityCreateRequest requestBody,
            HttpServletRequest request
    ) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Activity activity = new Activity();
        activity.setType(requestBody.getType());
        activity.setDate(requestBody.getDate());
        activity.setDurationMinutes(requestBody.getDurationMinutes());
        activity.setDistanceKm(requestBody.getDistanceKm());
        activity.setCaloriesBurned(requestBody.getCaloriesBurned());
        activity.setNotes(requestBody.getNotes());
        activity.setUser(user);

        Activity saved = activityService.create(activity);
        ActivityDto dto = toDto(saved);

        ApiResponse<ActivityDto> response = ApiResponse.success(
                "Activity created successfully",
                dto,
                HttpStatus.CREATED.value(),
                request.getRequestURI()
        );

        return ResponseEntity
                .created(URI.create("/api/activities/" + saved.getId()))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityDto>> getById(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Activity activity = activityService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

        ActivityDto dto = toDto(activity);

        ApiResponse<ActivityDto> response = ApiResponse.success(
                "Activity fetched successfully",
                dto,
                HttpStatus.OK.value(),
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ActivityDto>> update(
            @PathVariable Long id,
            @Valid @RequestBody ActivityUpdateRequest updated,
            HttpServletRequest request
    ) {
        Activity existing = activityService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

        existing.setType(updated.getType());
        existing.setDate(updated.getDate());
        existing.setDurationMinutes(updated.getDurationMinutes());
        existing.setDistanceKm(updated.getDistanceKm());
        existing.setCaloriesBurned(updated.getCaloriesBurned());
        existing.setNotes(updated.getNotes());

        Activity saved = activityService.update(existing);
        ActivityDto dto = toDto(saved);

        ApiResponse<ActivityDto> response = ApiResponse.success(
                "Activity updated successfully",
                dto,
                HttpStatus.OK.value(),
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        Activity existing = activityService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + id));

        activityService.delete(existing.getId());

        return ResponseEntity.noContent().build();
    }

    private ActivityDto toDto(Activity activity) {
        return new ActivityDto(
                activity.getId(),
                activity.getType(),
                activity.getDate(),
                activity.getDurationMinutes(),
                activity.getDistanceKm(),
                activity.getCaloriesBurned(),
                activity.getNotes(),
                activity.getUser() != null ? activity.getUser().getId() : null,
                activity.getCreatedAt()
        );
    }
}

package com.fitness.fitness_api.controller;

import com.fitness.fitness_api.dto.ApiResponse;
import com.fitness.fitness_api.dto.WeeklySummaryDto;
import com.fitness.fitness_api.service.ActivityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final ActivityService activityService;

    public DashboardController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<WeeklySummaryDto>> getWeeklySummary(
            @RequestParam Long userId,
            HttpServletRequest request
    ) {
        WeeklySummaryDto summary = activityService.getWeeklySummary(userId);

        ApiResponse<WeeklySummaryDto> response = ApiResponse.success(
                "Weekly summary fetched successfully",
                summary,
                HttpStatus.OK.value(),
                request.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }
}

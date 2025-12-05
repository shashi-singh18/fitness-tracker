package com.fitness.fitness_api.controller;

import com.fitness.fitness_api.dto.ApiResponse;
import com.fitness.fitness_api.dto.UserDto;
import com.fitness.fitness_api.dto.UserRegistrationRequest;
import com.fitness.fitness_api.entity.User;
import com.fitness.fitness_api.exception.ResourceNotFoundException;
import com.fitness.fitness_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody UserRegistrationRequest request,
            HttpServletRequest httpRequest
    ) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // later: hash it
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setWeightKg(request.getWeightKg());

        User saved = userService.register(user);
        UserDto dto = toDto(saved);

        ApiResponse<UserDto> response = ApiResponse.success(
                "User registered successfully",
                dto,
                HttpStatus.CREATED.value(),
                httpRequest.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getById(
            @PathVariable Long id,
            HttpServletRequest httpRequest
    ) {
        User user = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        UserDto dto = toDto(user);

        ApiResponse<UserDto> response = ApiResponse.success(
                "User fetched successfully",
                dto,
                HttpStatus.OK.value(),
                httpRequest.getRequestURI()
        );

        return ResponseEntity.ok(response);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getAge(),
                user.getWeightKg(),
                user.getCreatedAt()
        );
    }
}

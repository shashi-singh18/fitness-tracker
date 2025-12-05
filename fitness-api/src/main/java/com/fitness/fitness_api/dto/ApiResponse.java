package com.fitness.fitness_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String message;
    private T data;
    private int status;
    private String path;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(String message, T data, int status, String path) {
        return ApiResponse.<T>builder()
                .message(message)
                .data(data)
                .status(status)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message, int status, String path) {
        return ApiResponse.<T>builder()
                .message(message)
                .status(status)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}

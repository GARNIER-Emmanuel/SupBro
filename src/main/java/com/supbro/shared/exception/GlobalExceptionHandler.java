package com.supbro.shared.exception;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.supbro.friend.exception.FriendNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FriendNotFoundException.class)
    public ResponseEntity<ApiError> friendNotFound(FriendNotFoundException ex) {
        return ResponseEntity.status(404).body(new ApiError(
                404, "NOT_FOUND", ex.getMessage(), Instant.now(), Map.of()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> invalidRequest(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fields.putIfAbsent(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(new ApiError(
                400, "VALIDATION_ERROR", "Données invalides", Instant.now(), fields));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> unreadableRequest(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new ApiError(
                400, "INVALID_JSON", "JSON ou date invalide", Instant.now(), Map.of()));
    }
}
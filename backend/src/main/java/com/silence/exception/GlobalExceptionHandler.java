package com.silence.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Global Exception Handler for the SILENCE application.
 * Intercepts exceptions and converts them into stabilized JSON responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles business logic violations (e.g., writing more than one entry per
     * day).
     * 
     * @param e The caught IllegalStateException.
     * @return 409 Conflict with a descriptive message.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "error", "Conflict",
                "message", e.getMessage(),
                "timestamp", LocalDateTime.now()));
    }

    /**
     * Fallback handler for all unexpected server errors.
     * 
     * @param e The caught Exception.
     * @return 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Internal Server Error",
                "message", e.getMessage(),
                "timestamp", LocalDateTime.now()));
    }
}

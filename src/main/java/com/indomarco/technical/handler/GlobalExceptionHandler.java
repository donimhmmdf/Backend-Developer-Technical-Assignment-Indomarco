package com.indomarco.technical.handler;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.indomarco.technical.utility.ResponseUtil;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ResponseUtil responseUtil;

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException exception) {
        List<String> errors = exception.getConstraintViolations().stream()
                .map(e -> e.getPropertyPath() + ": " + e.getMessage()).toList();

        return responseUtil.errorResponse("Validation Failed.", errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(
            ResponseStatusException exception) {

        return responseUtil.errorResponse(
                "Operation Failed.",
                exception.getReason(),
                exception.getStatusCode());
    }

    @ExceptionHandler(value = { BadCredentialsException.class, UsernameNotFoundException.class })
    public ResponseEntity<?> handleBadCredentials(Exception exception) {
        return responseUtil.errorResponse("Login Failed", "Invalid username or password.",
                HttpStatus.UNAUTHORIZED);
    }

}

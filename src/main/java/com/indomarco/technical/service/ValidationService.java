package com.indomarco.technical.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final Validator validator;

    public void validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolation = validator.validate(request);

        if (constraintViolation.size() != 0) {
            throw new ConstraintViolationException(constraintViolation);
        }
    }
}
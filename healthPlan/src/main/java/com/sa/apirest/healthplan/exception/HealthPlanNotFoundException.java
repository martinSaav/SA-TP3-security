package com.sa.apirest.healthplan.exception;


import org.springframework.http.HttpStatus;

public class HealthPlanNotFoundException extends BusinessException {

    public HealthPlanNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

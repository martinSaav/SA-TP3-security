package com.sa.apirest.healthplan.exception;

import org.springframework.http.HttpStatus;

public class HealthPlanMismatchException extends BusinessException {

    public HealthPlanMismatchException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

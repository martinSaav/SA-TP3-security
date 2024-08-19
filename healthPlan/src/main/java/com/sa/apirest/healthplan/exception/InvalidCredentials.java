package com.sa.apirest.healthplan.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentials extends BusinessException {

    InvalidCredentials(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

package com.sa.apirest.healthplan.exception;

import org.springframework.http.HttpStatus;

public class UnknownErrorException extends BusinessException {

    public UnknownErrorException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}

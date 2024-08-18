package com.sistemasactivos.webclient.exception;

import com.sistemasactivos.webclient.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDto> userNotFoundException(BusinessException e) {
        ErrorDto errorDto = ErrorDto.builder()
                .statusCode(String.valueOf(e.getHttpStatus().value()))
                .status(e.getHttpStatus().getReasonPhrase())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, e.getHttpStatus());
    }
}


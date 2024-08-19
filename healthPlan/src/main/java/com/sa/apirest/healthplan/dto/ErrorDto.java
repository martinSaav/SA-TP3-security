package com.sa.apirest.healthplan.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDto {
    private String statusCode;
    private String status;
    private String message;
}

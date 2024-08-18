package com.sistemasactivos.webclient.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private String statusCode;
    private String status;
    private String message;
}

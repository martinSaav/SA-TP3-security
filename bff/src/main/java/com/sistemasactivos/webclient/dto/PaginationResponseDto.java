package com.sistemasactivos.webclient.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponseDto {
    private Object content;
    private Object pageable;
    private Boolean last;
    private Integer totalPages;
    private Integer totalElements;
    private Boolean first;
    private Integer size;
    private Integer number;
    private Object sort;
    private Integer numberOfElements;
    private Boolean empty;
}

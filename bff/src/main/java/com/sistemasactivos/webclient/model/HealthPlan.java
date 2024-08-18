package com.sistemasactivos.webclient.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HealthPlan {

    private Integer id;

    private String namePlan;

    private String documentPath;

    private Integer providerId;

    private Integer employId;

    private String clinics;

    private String comments;
}

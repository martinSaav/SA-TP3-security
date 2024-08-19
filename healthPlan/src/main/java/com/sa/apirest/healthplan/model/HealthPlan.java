package com.sa.apirest.healthplan.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "health_plan")
public class HealthPlan extends Base {

    @Column(name = "name_plan", nullable = false)
    private String namePlan;

    @Column(name = "document_path", nullable = false)
    private String documentPath;

    @Column(name = "provider_id", nullable = false)
    private Integer providerId;

    @Column(name = "employ_id", nullable = false)
    private Integer employId;

    @Column(name = "clinics", nullable = false)
    private String clinics;

    @Column(name = "comments", nullable = false)
    private String comments;
}

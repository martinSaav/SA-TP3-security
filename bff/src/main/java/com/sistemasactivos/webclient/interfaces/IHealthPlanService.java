package com.sistemasactivos.webclient.interfaces;

import com.sistemasactivos.webclient.dto.PaginationResponseDto;
import com.sistemasactivos.webclient.model.HealthPlan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IHealthPlanService {
    Flux<HealthPlan> findAll();

    Mono<PaginationResponseDto> findAllPaged(Integer page, Integer size, String sort);

    Mono<HealthPlan> findById(Integer id);

    Flux<HealthPlan> search(String filter);

    Mono<PaginationResponseDto> searchPaged(String filter, Integer page, Integer size, String sort);

    Mono<HealthPlan> save(HealthPlan healthPlan);

    Mono<HealthPlan> update(Integer id, HealthPlan healthPlan);

    Mono<Void> delete(Integer id);
}


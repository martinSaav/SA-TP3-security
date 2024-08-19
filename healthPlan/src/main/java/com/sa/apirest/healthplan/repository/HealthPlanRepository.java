package com.sa.apirest.healthplan.repository;


import com.sa.apirest.healthplan.model.HealthPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface HealthPlanRepository extends BaseRepository<HealthPlan, Integer> {

    @Query(nativeQuery = true)
    List<HealthPlan> searchNative(@Param("filter") String filter);

    @Query(nativeQuery = true)
    Page<HealthPlan> searchNative(@Param("filter") String filter, Pageable pageable);
}

package com.sa.apirest.healthplan.service;


import com.sa.apirest.healthplan.interfaces.HealthPlanService;
import com.sa.apirest.healthplan.model.HealthPlan;
import com.sa.apirest.healthplan.repository.BaseRepository;
import com.sa.apirest.healthplan.repository.HealthPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class HealthPlanServiceImpl extends BaseServiceImpl<HealthPlan, Integer> implements HealthPlanService {

    @Autowired
    private HealthPlanRepository healthPlanRepository;

    public HealthPlanServiceImpl(BaseRepository<HealthPlan, Integer> baseRepository) {
        super(baseRepository);
    }

    public List<HealthPlan> search(String filter) {
        List<HealthPlan> healthPlans = healthPlanRepository.searchNative(filter);
        if (healthPlans.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron registros con el filtro: " + filter);
        }
        return healthPlanRepository.searchNative(filter);
    }

    @Override
    public Page<HealthPlan> search(String filter, Pageable pageable) {
        Page<HealthPlan> healthPlans = healthPlanRepository.searchNative(filter, pageable);
        if (healthPlans.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron registros con el filtro: " + filter + " y paginación: " + pageable.toString());
        }
        return healthPlanRepository.searchNative(filter, pageable);
    }
}

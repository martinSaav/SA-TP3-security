package com.sa.apirest.healthplan.service;


import com.sa.apirest.healthplan.interfaces.BaseService;
import com.sa.apirest.healthplan.model.Base;
import com.sa.apirest.healthplan.repository.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;


public abstract class BaseServiceImpl<E extends Base, ID extends Serializable> implements BaseService<E, ID> {

    protected BaseRepository<E, ID> baseRepository;


    public BaseServiceImpl(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public List<E> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return baseRepository.findAll(pageable);
    }

    @Override
    public E findById(ID id) {
        return findByIdAux(id);
    }

    @Transactional
    @Override
    public E save(E entity) {
        return baseRepository.save(entity);
    }

    @Transactional
    @Override
    public E update(ID id, E entity) {
        if (entity.getId() != null && !id.equals(entity.getId())) {
            throw new IllegalArgumentException("El id del body no coincide con el id de la url");
        }
        E entityFound = findByIdAux(id);
        BeanUtils.copyProperties(entity, entityFound, "id");
        return baseRepository.save(entityFound);
    }

    @Transactional
    @Override
    public boolean delete(ID id) {
        E entityFound = findByIdAux(id);
        baseRepository.delete(entityFound);
        return true;
    }

    private E findByIdAux(ID id) {
        return baseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La entidad con el id: " + id + " no existe"));
    }
}

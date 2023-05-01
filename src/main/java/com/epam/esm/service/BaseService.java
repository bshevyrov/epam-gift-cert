package com.epam.esm.service;

import com.epam.esm.repository.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface BaseService<E extends BaseEntity> {
    long create(E entity);

    E findById(long id);

    List<E> findAll();

    void update(Map<String, Object> updates);

    void delete(long id);

}

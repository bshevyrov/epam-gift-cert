package com.epam.esm.service;

import com.epam.esm.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface BaseService<E extends BaseEntity> {
    long create(E entity);

    E findById(long id);

    List<E> findAll();

    void update(E entity);

    void delete(long id);

}

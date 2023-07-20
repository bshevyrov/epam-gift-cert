package com.epam.esm.dao;

import com.epam.esm.entity.Entity;

import java.util.List;

public interface BaseDAO<E extends Entity> {
    E findById(long id);

    List<E> findAll();

    void deleteById(long id);

    long create(E entity);

    void update(E entity);

    boolean existById(long entityId);
}

package com.epam.esm.service;

import com.epam.esm.controller.dto.BaseDTO;
import com.epam.esm.controller.dto.GiftCertificateDTO;
import com.epam.esm.repository.entity.BaseEntity;

import java.util.List;

public interface BaseService<E extends BaseEntity> {
    long create(E entity);
    E findById(long id);
    List<E> findAll();
    void update(E entity);
    void delete(long id);

}

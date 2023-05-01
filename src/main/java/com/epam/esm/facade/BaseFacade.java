package com.epam.esm.facade;

import com.epam.esm.veiw.dto.BaseDTO;

import java.util.List;
import java.util.Map;

public interface BaseFacade<E extends BaseDTO> {
    long create(E entity);

    E findById(long id);

    List<E> findAll();

    void update(Map<String,Object> updates);

    void delete(long id);
}

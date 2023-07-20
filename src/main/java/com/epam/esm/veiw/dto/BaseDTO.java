package com.epam.esm.veiw.dto;

import lombok.Data;

/**
 * BaseDTO is the superclass to all DtoRequest entities
 */
@Data
public abstract class BaseDTO {

    private long id;
    private String name;

}

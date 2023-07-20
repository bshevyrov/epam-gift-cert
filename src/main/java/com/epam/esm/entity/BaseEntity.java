package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Parent of all entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class BaseEntity implements Entity {
    private long id;
    private String name;

}

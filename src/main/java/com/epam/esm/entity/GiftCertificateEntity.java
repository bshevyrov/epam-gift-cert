package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity that represent gift_certificate table.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GiftCertificateEntity extends BaseEntity {
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagEntity> tagEntities;
}

package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity that represent gift_certificate_has_tag table.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class GiftCertificateTag extends AbstractEntity {
    private long giftCertificateId;
    private long tagId;
}

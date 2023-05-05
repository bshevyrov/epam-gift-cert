package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    List<GiftCertificate> findByTagName(String name);

    List<GiftCertificate> findAll(Optional<String> certName, Optional<String> description, String sortField, String sortType);
}

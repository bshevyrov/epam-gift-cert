package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificate> {

    List<GiftCertificate> findAll(Optional<String> tagName, Optional<String> giftCertificateName, Optional<String> description, String sortField, String sortType);
}

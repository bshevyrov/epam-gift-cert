package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {
    List<GiftCertificate> findAll(Optional<String> tagName, Optional<String> giftCertificateName, Optional<String> description, String sort, String sortField);
}

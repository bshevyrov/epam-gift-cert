package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {
    List<GiftCertificate> findByTagName(String name);
    boolean existById(long id);

    List<GiftCertificate> findAll(Optional<String> certName, Optional<String> description, String sort, String sortField);
}

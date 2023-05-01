package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {
    List<GiftCertificate> findByTagName(String name);
}

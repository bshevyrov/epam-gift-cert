package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificate>{
    List<GiftCertificate> findByTagName(String name);
}

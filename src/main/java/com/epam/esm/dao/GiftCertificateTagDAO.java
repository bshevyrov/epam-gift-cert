package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateTagEntity;

public interface GiftCertificateTagDAO extends BaseDAO<GiftCertificateTagEntity> {
    void deleteByGiftCertificateId(long id);
}

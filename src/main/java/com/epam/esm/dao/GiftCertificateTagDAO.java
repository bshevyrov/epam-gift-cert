package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateTag;

public interface GiftCertificateTagDAO extends BaseDAO<GiftCertificateTag> {
    void deleteByGiftCertificateId(long id);
}

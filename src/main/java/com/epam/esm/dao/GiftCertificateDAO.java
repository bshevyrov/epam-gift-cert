package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.veiw.SearchRequest;

import java.util.List;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificateEntity> {
    List<GiftCertificateEntity> findAll(SearchRequest searchRequest);
}

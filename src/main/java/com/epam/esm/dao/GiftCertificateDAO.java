package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.veiw.SearchRequest;

import java.util.List;

public interface GiftCertificateDAO extends BaseDAO<GiftCertificate> {
    List<GiftCertificate> findAll(SearchRequest searchRequest);
}

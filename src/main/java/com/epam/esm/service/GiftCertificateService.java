package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.veiw.SearchRequest;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificateEntity> {

    List<GiftCertificateEntity> findAll(SearchRequest searchRequest);
}

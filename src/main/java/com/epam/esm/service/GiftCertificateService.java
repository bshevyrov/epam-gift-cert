package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.veiw.SearchRequest;

import java.util.List;

public interface GiftCertificateService extends BaseService<GiftCertificate> {

    List<GiftCertificate> findAll(SearchRequest searchRequest);
}

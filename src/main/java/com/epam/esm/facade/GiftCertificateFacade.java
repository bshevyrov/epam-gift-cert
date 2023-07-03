package com.epam.esm.facade;

import com.epam.esm.veiw.SearchRequest;
import com.epam.esm.veiw.dto.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateFacade extends BaseFacade<GiftCertificateDTO> {

    List<GiftCertificateDTO> findAll(SearchRequest searchRequest);
}
package com.epam.esm.facade;

import com.epam.esm.veiw.dto.GiftCertificateDTO;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateFacade extends BaseFacade<GiftCertificateDTO> {

    List<GiftCertificateDTO> findAll(Optional<String> tagName, Optional<String> giftCertificateName, Optional<String> description, String sortField, String sortType);
}

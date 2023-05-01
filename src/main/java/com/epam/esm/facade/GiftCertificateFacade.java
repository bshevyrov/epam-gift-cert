package com.epam.esm.facade;

import com.epam.esm.veiw.dto.GiftCertificateDTO;

import java.util.List;

public interface GiftCertificateFacade extends BaseFacade<GiftCertificateDTO> {
    List<GiftCertificateDTO> findByTagName(String name);
}

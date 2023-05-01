package com.epam.esm.mapper;

import com.epam.esm.controller.dto.GiftCertificateDTO;
import com.epam.esm.repository.entity.GiftCertificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface GiftCertificateMapper {
    GiftCertificateDTO toDTO(GiftCertificate giftCertificate);

    GiftCertificate toModel(GiftCertificateDTO giftCertificateDTO);
}

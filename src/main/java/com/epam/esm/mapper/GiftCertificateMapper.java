package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.veiw.dto.GiftCertificateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface GiftCertificateMapper {
    GiftCertificateDTO toDTO(GiftCertificate giftCertificate);

    GiftCertificate toModel(GiftCertificateDTO giftCertificateDTO);
}

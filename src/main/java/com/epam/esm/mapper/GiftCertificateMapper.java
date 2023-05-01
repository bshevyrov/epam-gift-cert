package com.epam.esm.mapper;

import com.epam.esm.veiw.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface GiftCertificateMapper {
    @Mapping(target = "tags",ignore = true)
    GiftCertificateDTO toDTO(GiftCertificate giftCertificate);

    GiftCertificate toModel(GiftCertificateDTO giftCertificateDTO);
}

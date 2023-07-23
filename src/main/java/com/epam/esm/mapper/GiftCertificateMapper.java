package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.veiw.dto.GiftCertificateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface GiftCertificateMapper {
    GiftCertificateDTO toDTO(GiftCertificateEntity giftCertificateEntity);

    GiftCertificateEntity toModel(GiftCertificateDTO giftCertificateDTO);
}

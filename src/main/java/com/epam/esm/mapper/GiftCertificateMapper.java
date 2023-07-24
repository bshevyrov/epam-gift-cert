package com.epam.esm.mapper;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.veiw.dto.GiftCertificateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TagListMapper.class)
public interface GiftCertificateMapper {
    @Mapping(target="tagDTOS", source="tagEntities")
    GiftCertificateDTO toDTO(GiftCertificateEntity giftCertificateEntity);

    @Mapping(target="tagEntities", source="tagDTOS")
    GiftCertificateEntity toModel(GiftCertificateDTO giftCertificateDTO);
}

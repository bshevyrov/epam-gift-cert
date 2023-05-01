package com.epam.esm.mapper;

import com.epam.esm.veiw.dto.GiftCertificateDTO;
import com.epam.esm.entity.GiftCertificate;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = GiftCertificateMapper.class)
public interface GiftCertificateListMapper {
    List<GiftCertificateDTO> toDTOList(List<GiftCertificate> list);

    List<GiftCertificate> toModelList(List<GiftCertificateDTO> list);
}

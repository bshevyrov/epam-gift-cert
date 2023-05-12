package com.epam.esm.facade.impl;

import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.mapper.GiftCertificateListMapper;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagListMapper;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateTagServiceImpl;
import com.epam.esm.veiw.dto.GiftCertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GiftCertificateFacadeImpl implements GiftCertificateFacade {

    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateListMapper giftCertificateListMapper;
    private final GiftCertificateService giftCertificateService;
    private final TagListMapper tagListMapper;
    private final TagService tagService;

    @Autowired
    public GiftCertificateFacadeImpl(GiftCertificateMapper giftCertificateMapper, GiftCertificateListMapper giftCertificateListMapper, GiftCertificateService giftCertificateService, TagListMapper tagListMapper, TagService tagService) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateListMapper = giftCertificateListMapper;
        this.giftCertificateService = giftCertificateService;
        this.tagListMapper = tagListMapper;
        this.tagService = tagService;
    }

    @Override
    public long create(GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.create(giftCertificateMapper.toModel(giftCertificateDTO));
    }

    @Override
    public GiftCertificateDTO findById(long id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateMapper.toDTO(giftCertificateService.findById(id));
        giftCertificateDTO.setTags(tagListMapper.toDTOList(tagService.findAllByGiftCertificateId(id)));
        return giftCertificateDTO;
    }

    @Override
    public List<GiftCertificateDTO> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<GiftCertificateDTO> findAll(Optional<String> certName, Optional<String> description, String sortField, String sortType) {
        return giftCertificateListMapper.toDTOList(giftCertificateService.findAll(certName, description, sortField, sortType));
    }

    @Override
    public void update(Map<String, Object> updates) {
       giftCertificateService.update(updates);
    }

    @Override
    public void delete(long id) {
        giftCertificateService.delete(id);
    }

    @Override
    public List<GiftCertificateDTO> findAllByTagName(String name) {
        return giftCertificateListMapper.toDTOList(giftCertificateService.findByTagName(name));
    }
}

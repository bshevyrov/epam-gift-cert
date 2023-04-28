package com.epam.esm.facade.impl;

import com.epam.esm.controller.dto.GiftCertificateDTO;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.mapper.GiftCertificateListMapper;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftCertificateFacadeImpl implements GiftCertificateFacade {

    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateListMapper giftCertificateListMapper;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateFacadeImpl(GiftCertificateMapper giftCertificateMapper, GiftCertificateListMapper giftCertificateListMapper, GiftCertificateService giftCertificateService) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateListMapper = giftCertificateListMapper;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public long create(GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.create(giftCertificateMapper.toModel(giftCertificateDTO));
    }

    @Override
    public GiftCertificateDTO findById(long id) {
        return giftCertificateMapper.toDTO(giftCertificateService.findById(id));
    }

    @Override
    public List<GiftCertificateDTO> findAll() {
        return giftCertificateListMapper.toDTOList(giftCertificateService.findAll());
    }

    @Override
    public void update(GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.update(giftCertificateMapper.toModel(giftCertificateDTO));
    }

    @Override
    public void delete(long id) {
        giftCertificateService.delete(id);
    }
}

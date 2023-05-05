package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        return giftCertificateDAO.create(giftCertificate);
    }

    @Override
    public GiftCertificate findById(long id) {
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id);
        giftCertificate.setTags(tagDAO.findByGiftCertificateId(giftCertificate.getId()));
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<GiftCertificate> findAll(Optional<String> certName, Optional<String> description, String sortField, String sortType) {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findAll(certName, description, sortField, sortType);
        giftCertificateList.forEach(giftCertificate -> {
            giftCertificate.setTags(tagDAO.findByGiftCertificateId(giftCertificate.getId()));
        });
        return giftCertificateList;
    }

    @Override
    public void update(Map<String, Object> updates) {
        giftCertificateDAO.update(updates);
    }

    @Override
    public void delete(long id) {
        giftCertificateDAO.deleteById(id);
    }

    @Override
    public List<GiftCertificate> findByTagName(String name) {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findByTagName(name);
        giftCertificateList.forEach(giftCertificate -> {
            giftCertificate.setTags(tagDAO.findByGiftCertificateId(giftCertificate.getId()));
        });
        return giftCertificateList;
    }
}

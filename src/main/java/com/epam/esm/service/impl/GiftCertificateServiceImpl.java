package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateTagDAO giftCertificateTagDAO;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, GiftCertificateTagDAO giftCertificateTagDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateTagDAO = giftCertificateTagDAO;
        this.tagDAO = tagDAO;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public long create(GiftCertificate giftCertificate) {
        long giftCertificateId = giftCertificateDAO.create(giftCertificate);
        giftCertificate.getTags().forEach(tag -> {
            if (tagDAO.existByName(tag.getName())) {
                giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificateId, tagDAO.findByName(tag.getName()).getId()));
            } else {
                long tagId = tagDAO.create(new Tag(tag.getName()));
                giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificateId, tagId));
            }
        });
        return giftCertificateId;
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

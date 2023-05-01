package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.repository.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDAO giftCertificateDAO;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
    }

    @Override
    public long create(GiftCertificate giftCertificate) {
        return giftCertificateDAO.create(giftCertificate);
    }

    @Override
    public GiftCertificate findById(long id) {
        return giftCertificateDAO.findById(id);
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateDAO.findAll();
    }

    @Override
    public void update(Map<String, Object> updates) {
        giftCertificateDAO.update(updates);
    }

    @Override
    public void delete(long id) {
        giftCertificateDAO.deleteById(id);
    }
}

package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.service.GiftCertificateTagService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateTagServiceImpl implements GiftCertificateTagService {

    private final GiftCertificateTagDAO gstDAO;

    public GiftCertificateTagServiceImpl(GiftCertificateTagDAO gstDAO) {
        this.gstDAO = gstDAO;
    }

    @Override
    public long create(GiftCertificateTag gct) {
        return gstDAO.create(gct);
    }

    @Override
    public GiftCertificateTag findById(long id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Map updates) {

    }
}

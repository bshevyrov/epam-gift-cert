package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.exception.GiftCertificateIdException;
import com.epam.esm.exception.TagIdException;
import com.epam.esm.service.GiftCertificateTagService;
import com.epam.esm.util.InputVerification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftCertificateTagServiceImpl implements GiftCertificateTagService {
    private final GiftCertificateTagDAO giftCertificateTagDAO;

    public GiftCertificateTagServiceImpl(GiftCertificateTagDAO giftCertificateTagDAO) {
        this.giftCertificateTagDAO = giftCertificateTagDAO;
    }

    @Override
    public long create(GiftCertificateTag giftCertificateTag) {
        if (!InputVerification.verifyId(giftCertificateTag.getGiftCertificateId())) {
            throw new GiftCertificateIdException(giftCertificateTag.getGiftCertificateId());
        }
        if (!InputVerification.verifyId(giftCertificateTag.getTagId())) {
            throw new TagIdException(giftCertificateTag.getTagId());
        }
        return giftCertificateTagDAO.create(giftCertificateTag);
    }

    @Override
    public GiftCertificateTag findById(long id) {
        return giftCertificateTagDAO.findById(id);
    }

    @Override
    public List<GiftCertificateTag> findAll() {
        return giftCertificateTagDAO.findAll();
    }

    @Override
    public void delete(long id) {
        giftCertificateTagDAO.deleteById(id);
    }

    @Override
    public void update(GiftCertificateTag giftCertificateTag) {
        giftCertificateTagDAO.update(giftCertificateTag);
    }
}

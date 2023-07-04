package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagIdException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.GiftCertificateTagService;
import com.epam.esm.util.InputVerification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * * Used  to manipulate GiftCertificateTag objects and collecting data.
 */
@Service
public class GiftCertificateTagServiceImpl implements GiftCertificateTagService {
    private final GiftCertificateTagDAO giftCertificateTagDAO;
    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    public GiftCertificateTagServiceImpl(GiftCertificateTagDAO giftCertificateTagDAO, GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateTagDAO = giftCertificateTagDAO;
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    /**
     * Method create relationship between tag and giftCertificate.
     * Checks if giftCertificate id valid
     * - if false throw {@link  GiftCertificateIdException}
     * Then  checks if giftCertificate exist
     * - if false throws (@code GiftCertificateNotFoundException) exception
     * Then checks if tag id valid
     * - if false throw {@link  TagIdException}
     * Then checks if tag exist
     * - if false throws (@code TagNotFoundException) exception
     *
     * @param giftCertificateTag entity with tag and giftCertificate ids
     * @return null
     */
    @Override
    public long create(GiftCertificateTag giftCertificateTag) {
        if (!InputVerification.verifyId(giftCertificateTag.getGiftCertificateId())) {
            throw new GiftCertificateIdException(giftCertificateTag.getGiftCertificateId());
        }
        if (!tagDAO.existById(giftCertificateTag.getGiftCertificateId())) {
            throw new GiftCertificateNotFoundException(giftCertificateTag.getGiftCertificateId());
        }

        if (!InputVerification.verifyId(giftCertificateTag.getTagId())) {
            throw new TagIdException(giftCertificateTag.getTagId());
        }
        if (!giftCertificateDAO.existById(giftCertificateTag.getTagId())) {
            throw new TagNotFoundException(giftCertificateTag.getTagId());
        }

        return giftCertificateTagDAO.create(giftCertificateTag);
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public GiftCertificateTag findById(long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public List<GiftCertificateTag> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public void update(GiftCertificateTag giftCertificateTag) {
        throw new UnsupportedOperationException();
    }
}

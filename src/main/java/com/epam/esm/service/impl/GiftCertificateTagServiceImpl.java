package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateTagEntity;
import com.epam.esm.exception.giftcertificate.GiftCertificateInvalidIdException;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagInvalidIdException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.GiftCertificateTagService;
import com.epam.esm.util.InputVerification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
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
     * - if false throw {@link  GiftCertificateInvalidIdException}
     * Then  checks if giftCertificate exist
     * - if false throws (@code GiftCertificateNotFoundException) exception
     * Then checks if tag id valid
     * - if false throw {@link  TagInvalidIdException}
     * Then checks if tag exist
     * - if false throws (@code TagNotFoundException) exception
     *
     * @param giftCertificateTagEntity entity with tag and giftCertificate ids
     * @return null
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public long create(GiftCertificateTagEntity giftCertificateTagEntity) {
        if (!InputVerification.verifyId(giftCertificateTagEntity.getGiftCertificateId())) {
            throw new GiftCertificateInvalidIdException(giftCertificateTagEntity.getGiftCertificateId());
        }
        if (!tagDAO.existById(giftCertificateTagEntity.getGiftCertificateId())) {
            throw new GiftCertificateNotFoundException(giftCertificateTagEntity.getGiftCertificateId());
        }

        if (!InputVerification.verifyId(giftCertificateTagEntity.getTagId())) {
            throw new TagInvalidIdException(giftCertificateTagEntity.getTagId());
        }
        if (!giftCertificateDAO.existById(giftCertificateTagEntity.getTagId())) {
            throw new TagNotFoundException(giftCertificateTagEntity.getTagId());
        }

        return giftCertificateTagDAO.create(giftCertificateTagEntity);
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public GiftCertificateTagEntity findById(long id) {
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
    public List<GiftCertificateTagEntity> findAll() {
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
    public void update(GiftCertificateTagEntity giftCertificateTagEntity) {
        throw new UnsupportedOperationException();
    }
}

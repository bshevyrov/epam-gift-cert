package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateTagEntity;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.GiftCertificateTagService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private final MessageSource messageSource;

    public GiftCertificateTagServiceImpl(GiftCertificateTagDAO giftCertificateTagDAO, GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO, MessageSource messageSource) {
        this.giftCertificateTagDAO = giftCertificateTagDAO;
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.messageSource = messageSource;
    }

    /**
     * Method create relationship between tag and giftCertificate.
     * Checks if giftCertificate exist
     * - if false throws (@code GiftCertificateNotFoundException) exception
     * Then checks if tag exist
     * - if false throws (@code TagNotFoundException) exception
     *
     * @param giftCertificateTagEntity entity with tag and giftCertificate ids
     * @return null
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public long create(GiftCertificateTagEntity giftCertificateTagEntity) {

        if (!tagDAO.existById(giftCertificateTagEntity.getGiftCertificateId())) {
            throw new GiftCertificateNotFoundException(messageSource.getMessage("giftcertificate.notfound.exception",
                    new Object[]{giftCertificateTagEntity.getGiftCertificateId()},
                    LocaleContextHolder.getLocale()));
        }
        if (!giftCertificateDAO.existById(giftCertificateTagEntity.getTagId())) {
            throw new TagNotFoundException(messageSource.getMessage("tag.notfound.exception",
                    new Object[]{giftCertificateTagEntity.getTagId()},
                    LocaleContextHolder.getLocale()));
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

package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagExistException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


/**
 * Used  to manipulate GiftCertificate objects and collecting data.
 */

@Service
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;
    private final GiftCertificateDAO giftCertificateDAO;
    private final MessageSource messageSource;


    @Autowired
    public TagServiceImpl(TagDAO tagDAO, GiftCertificateDAO giftCertificateDAO, MessageSource messageSource) {
        this.tagDAO = tagDAO;
        this.giftCertificateDAO = giftCertificateDAO;
        this.messageSource = messageSource;
    }

    /**
     * Method creates tag.
     * Checks if tag with this name exist
     * - if true throws TagExistException
     *
     * @param tagEntity object for creation
     * @return id fon created object
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public long create(TagEntity tagEntity) {
        if (tagDAO.existByName(tagEntity.getName())) {
            throw new TagExistException(messageSource.getMessage("tag.name.exception", new Object[]{tagEntity.getName()}, LocaleContextHolder.getLocale()));
        }
        return tagDAO.create(tagEntity);
    }

    /**
     * Method return tag that was found by id
     * Checks if tag with this name exist
     * - if false throws TagNotFoundException
     *
     * @param id tag id values
     * @return tag entity
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public TagEntity findById(long id) {
        if (!tagDAO.existById(id)) {
            throw new TagNotFoundException(messageSource.getMessage("tag.notfound.exception",new Object[]{id},LocaleContextHolder.getLocale()));
        }
        return tagDAO.findById(id);
    }

    /**
     * Method finds all tags
     *
     * @return List of tags
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public List<TagEntity> findAll() {
        return tagDAO.findAll();
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public void update(TagEntity tagEntity) {
        throw new UnsupportedOperationException();
    }

    /**
     * Method deletes tag.
     * Checks if tag exist by id
     * - if false throws TagNotFoundException
     *
     * @param id request parameter
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void delete(long id) {
        if (!tagDAO.existById(id)) {
            throw new TagNotFoundException(messageSource.getMessage("tag.notfound.exception",new Object[]{id},LocaleContextHolder.getLocale()));
        }
        tagDAO.deleteById(id);
    }

    /**
     * Method finds all tags by connected giftCertificate.
     * Checks  if giftCertificate exist by id
     * * - if false throws GiftCertificateNotFoundException
     *
     * @param id giftCertificate id
     * @return List of tags
     */

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public List<TagEntity> findAllByGiftCertificateId(long id) {
        if (!giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException(messageSource.getMessage("giftcertificate.notfound.exception",
                    new Object[]{id},
                    LocaleContextHolder.getLocale()));
        }
        return tagDAO.findAllByGiftCertificateId(id);
    }
}

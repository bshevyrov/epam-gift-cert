package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.giftcertificate.GiftCertificateInvalidIdException;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagExistException;
import com.epam.esm.exception.tag.TagInvalidIdException;
import com.epam.esm.exception.tag.TagInvalidNameException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.util.InputVerification;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, GiftCertificateDAO giftCertificateDAO) {
        this.tagDAO = tagDAO;
        this.giftCertificateDAO = giftCertificateDAO;
    }

    /**
     * Method creates tag.
     * Checks if tag have valid name
     * - if false throws{@link  TagInvalidNameException}
     * Checks if tag with this name exist
     * - if true throws TagExistException
     *
     * @param tagEntity object for creation
     * @return id fon created object
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public long create(TagEntity tagEntity) {
        if (!InputVerification.verifyName(tagEntity.getName())) {
            throw new TagInvalidNameException(tagEntity.getName());
        }
        if (!tagDAO.existByName(tagEntity.getName())) {
            throw new TagExistException(tagEntity.getName());
        }
        return tagDAO.create(tagEntity);
    }

    /**
     * Method return tag that was found by id
     * Checks if tag have valid id
     * - if false throws{@link  TagInvalidIdException}
     * Checks if tag with this name exist
     * - if false throws TagNotFoundException
     *
     * @param id tag id values
     * @return tag entity
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public TagEntity findById(long id) {
        if (!InputVerification.verifyId(id)) {
            throw new TagInvalidIdException(id);
        }
        if (!tagDAO.existById(id)) {
            throw new TagNotFoundException(id);
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
     * Checks if id valid
     * - if false throws TagIdException
     * Then checks if tag exist by id
     * - if false throws TagNotFoundException
     *
     * @param id request parameter
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void delete(long id) {
        if (!InputVerification.verifyId(id)) {
            throw new TagInvalidIdException(id);
        }
        if (!tagDAO.existById(id)) {
            throw new TagNotFoundException(id);
        }
        tagDAO.deleteById(id);
    }

    /**
     * Method finds all tags by connected giftCertificate.
     * Checks if id valid
     * * - if false throws GiftCertificateIdException exception
     * * Then checks if giftCertificate exist by id
     * * - if false throws GiftCertificateNotFoundException
     *
     * @param id giftCertificate id
     * @return List of tags
     */

    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public List<TagEntity> findAllByGiftCertificateId(long id) {
        if (!InputVerification.verifyId(id)) {
            throw new GiftCertificateInvalidIdException(id);
        }
        if (!giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException(id);
        }
        return tagDAO.findAllByGiftCertificateId(id);
    }
}

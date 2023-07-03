package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagExistException;
import com.epam.esm.exception.tag.TagIdException;
import com.epam.esm.exception.tag.TagNameException;
import com.epam.esm.exception.tag.TagNotFoundException;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.util.InputVerification.verifyId;
import static com.epam.esm.util.InputVerification.verifyName;

/**
 * Used  to manipulate GiftCertificate objects and collecting data.
 */

@Component
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
     * - if false throws{@code TagNameException}
     * Checks if tag with this name exist
     * - if true throws TagExistException
     *
     * @param tag object for creation
     * @return id fon created object
     */
    @Override
    public long create(Tag tag) {
        if (!verifyName(tag.getName())) {
            throw new TagNameException(tag.getName());
        }
        if (tagDAO.existByName(tag.getName())) {
            throw new TagExistException(tag.getName());
        }
        return tagDAO.create(tag);
    }

    /**
     * Method return tag that was found by id
     * Checks if tag have valid id
     * - if false throws{@code TagIdException}
     * Checks if tag with this name exist
     * - if false throws TagNotFoundException
     *
     * @param id tag id values
     * @return tag entity
     */
    @Override
    public Tag findById(long id) {
        if (!verifyId(id)) {
            throw new TagIdException(id);
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
    public List<Tag> findAll() {
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
    public void update(Tag tag) {
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
    public void delete(long id) {
        if (!verifyId(id)) {
            throw new TagIdException(id);
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
    public List<Tag> findAllByGiftCertificateId(long id) {
        if (!verifyId(id)) {
            throw new GiftCertificateIdException(id);
        }
        if (!giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException(id);
        }
        return tagDAO.findAllByGiftCertificateId(id);
    }
}

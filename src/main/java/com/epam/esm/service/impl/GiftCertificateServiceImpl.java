package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagNameException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.InputVerification;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Used  to manipulate GiftCertificate objects and collecting data.
 */
@Service
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

    /**
     * Method creates gift certificate.
     * Checks if tag have valid name
     * - if false throws{@code TagNameException}
     * Checks if tag with this name exist
     * - if true add tag id to giftCertificate
     * - if false create new tag and add id of this tag to gift certificate
     *
     * @param giftCertificate object for creation
     * @return id fon created object
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public long create(GiftCertificate giftCertificate) {
        long giftCertificateId = giftCertificateDAO.create(giftCertificate);
        giftCertificate.getTags().forEach(tag -> {
            if (!StringUtils.isAlphanumeric(tag.getName())) {
                throw new TagNameException(tag.getName());
            }
            if (tagDAO.existByName(tag.getName())) {
                giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificateId, tagDAO.findByName(tag.getName()).getId()));
            } else {
                long tagId = tagDAO.create(new Tag(tag.getName()));
                giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificateId, tagId));
            }
        });
        return giftCertificateId;
    }

    /**
     * Method finds gift certificate by id
     * Checks if gift certificate exists:
     * - if true - finds gift certificate by id and set tags to it
     * - if false - throws {@code GiftCertificateNotFoundException} exception
     *
     * @param id requested parameter
     * @return {@code GiftCertificate} found object
     */
    @Override
    public GiftCertificate findById(long id) {
        if (!InputVerification.verifyId(id)) {
            throw new GiftCertificateIdException(id);
        }
        if (giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException(id);

        }
        GiftCertificate giftCertificate = giftCertificateDAO.findById(id);
        giftCertificate.setTags(tagDAO.findAllByGiftCertificateId(giftCertificate.getId()));
        return giftCertificate;
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public List<GiftCertificate> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method finds all gift certificates based on search param, add tags for each one, and sort items.
     *
     * @param tagName             optional tag name
     * @param giftCertificateName optional giftCertificate name
     * @param description         optional description
     * @param sortField           name or date
     * @param sortType            asc or desc
     * @return List of objects
     */
    @Override
    public List<GiftCertificate> findAll(Optional<String> tagName, Optional<String> giftCertificateName, Optional<String> description, String sortField, String sortType) {
        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findAll(tagName, giftCertificateName, description, sortField, sortType);
        giftCertificateList.forEach(giftCertificate -> {
            giftCertificate.setTags(tagDAO.findAllByGiftCertificateId(giftCertificate.getId()));
        });
        return giftCertificateList;
    }

    /**
     * Method updates gift certificate.
     * Checks if giftCertificate  is exists by id:
     * - if true proceed to next operation
     * - if false throws GiftCertificateNotFoundException exception
     * Then checks if tags set is null or empty:
     * - if true - doesn't  proceed to update tags
     * - if false - removes all connection with  tags,  and checks is tag name correct
     * - if false throws TagNameException
     * - if true  checks if tags already exist
     * - if true adds tag id to giftCertificate and creates relation between tag and giftCertificate
     * - if false creates new tag and adds tag id to giftCertificate, creates relation between tag and giftCertificate
     *
     * @param giftCertificate candidate for update
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void update(GiftCertificate giftCertificate) {
        if (giftCertificateDAO.existById(giftCertificate.getId())) {
            throw new GiftCertificateNotFoundException(giftCertificate.getId());
        }
        if (ListUtils.emptyIfNull(giftCertificate.getTags()).size() > 0) {
            giftCertificateTagDAO.deleteByGiftCertificateId(giftCertificate.getId());
            giftCertificate.getTags().forEach(tag -> {
                if (!StringUtils.isAlphanumeric(tag.getName())) {
                    throw new TagNameException(tag.getName());
                }
                if (tagDAO.existByName(tag.getName())) {
                    giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagDAO.findByName(tag.getName()).getId()));
                } else {
                    long tagId = tagDAO.create(new Tag(tag.getName()));
                    giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagId));
                }
            });
        }
        giftCertificate.setTags(null);
        giftCertificateDAO.update(giftCertificate);
    }

    /**
     * Method deletes gift certificate
     * Checks if gift certificate id is valid:
     * - if false throws {@code GiftCertificateIdException}
     * Checks if gift certificate exists by id:
     * - if true - deletes from database
     * - if false - throws GiftCertificateNotFoundException exception
     *
     * @param id requested parameter
     */
    @Override
    public void delete(long id) {
        if (!InputVerification.verifyId(id)) {
            throw new GiftCertificateIdException(id);
        }
        if (giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException(id);
        }
        giftCertificateDAO.deleteById(id);
    }

}

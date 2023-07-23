package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.GiftCertificateTagEntity;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.exception.tag.TagNameException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.InputVerification;
import com.epam.esm.veiw.SearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

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
     * - if false throws{@link  TagNameException}
     * Checks if tag with this name exist
     * - if true add tag id to giftCertificate
     * - if false create new tag and add id of this tag to gift certificate
     *
     * @param giftCertificateEntity object for creation
     * @return id fon created object
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public long create(GiftCertificateEntity giftCertificateEntity) {
        long giftCertificateId = giftCertificateDAO.create(giftCertificateEntity);
        giftCertificateEntity.getTagEntities().forEach(tag -> {
            if (!InputVerification.verifyName(tag.getName())) {
                throw new TagNameException(tag.getName());
            }
            if (tagDAO.existByName(tag.getName())) {
                giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateId, tagDAO.findByName(tag.getName()).getId()));
            } else {
                long tagId = tagDAO.create(tag);
                giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateId, tagId));
            }
        });
        return giftCertificateId;
    }

    /**
     * Method finds gift certificate by id
     * Checks if gift certificate exists:
     * - if true - finds gift certificate by id and set tags to it
     * - if false - throws {@link GiftCertificateNotFoundException} exception
     *
     * @param id requested parameter
     * @return {@link  GiftCertificateEntity} found object
     */
    @Override
    public GiftCertificateEntity findById(long id) {
        if (!InputVerification.verifyId(id)) {
            throw new GiftCertificateIdException(id);
        }
        if (!giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException(id);

        }
        GiftCertificateEntity giftCertificateEntity = giftCertificateDAO.findById(id);
        giftCertificateEntity.setTagEntities(tagDAO.findAllByGiftCertificateId(giftCertificateEntity.getId()));
        return giftCertificateEntity;
    }

    /**
     * Guaranteed to throw an exception and leave.
     *
     * @throws UnsupportedOperationException always
     * @deprecated Unsupported operation.
     */
    @Override
    @Deprecated
    public List<GiftCertificateEntity> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Method finds all gift certificates based on search param, add tags for each one, and sort items.
     *
     * @param searchRequest entity
     * @return List of objects
     */
    @Override
    public List<GiftCertificateEntity> findAll(SearchRequest searchRequest) {
        List<GiftCertificateEntity> giftCertificateEntityList = giftCertificateDAO.findAll(searchRequest);
        giftCertificateEntityList.forEach(giftCertificate -> giftCertificate.setTagEntities(tagDAO.findAllByGiftCertificateId(giftCertificate.getId())));
        return giftCertificateEntityList;
    }

    /**
     * Method updates gift certificate.
     * Checks if giftCertificate  if id valid:
     * - if true proceed to next operation
     * * - if false thrown {@link GiftCertificateIdException}
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
     * @param giftCertificateEntity candidate for update
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void update(GiftCertificateEntity giftCertificateEntity) {
        if (!InputVerification.verifyId(giftCertificateEntity.getId())) {
            throw new GiftCertificateIdException(giftCertificateEntity.getId());
        }
        if (!giftCertificateDAO.existById(giftCertificateEntity.getId())) {
            throw new GiftCertificateNotFoundException(giftCertificateEntity.getId());
        }
        if (giftCertificateEntity.getTagEntities() != null
                && giftCertificateEntity.getTagEntities().size() > 0) {
            giftCertificateTagDAO.deleteByGiftCertificateId(giftCertificateEntity.getId());
            giftCertificateEntity.getTagEntities().forEach(tag -> {
                if (!InputVerification.verifyName(tag.getName())) {
                    throw new TagNameException(tag.getName());
                }
                if (tagDAO.existByName(tag.getName())) {
                    giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagDAO.findByName(tag.getName()).getId()));
                } else {
                    long tagId = tagDAO.create(tag);
                    giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagId));
                }
            });
        }
        giftCertificateEntity.setTagEntities(null);
        giftCertificateDAO.update(giftCertificateEntity);
    }

    /**
     * Method deletes gift certificate
     * Checks if gift certificate id is valid:
     * - if false throws {@link  GiftCertificateIdException}
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
        if (!giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException(id);
        }
        giftCertificateDAO.deleteById(id);
    }
}

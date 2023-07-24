package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.GiftCertificateTagEntity;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.veiw.SearchRequest;
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
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final GiftCertificateTagDAO giftCertificateTagDAO;
    private final TagDAO tagDAO;
    private final MessageSource messageSource;


    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, GiftCertificateTagDAO giftCertificateTagDAO, TagDAO tagDAO, MessageSource messageSource) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.giftCertificateTagDAO = giftCertificateTagDAO;
        this.tagDAO = tagDAO;
        this.messageSource = messageSource;
    }

    /**
     * Method creates gift certificate.
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
    @Transactional(rollbackFor = {SQLException.class})
    public GiftCertificateEntity findById(long id) {

        if (!giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException((messageSource.getMessage("giftcertificate.notfound.exception",
                    new Object[]{id},
                    LocaleContextHolder.getLocale())));

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
    @Transactional(rollbackFor = {SQLException.class})
    public List<GiftCertificateEntity> findAll(SearchRequest searchRequest) {
        List<GiftCertificateEntity> giftCertificateEntityList = giftCertificateDAO.findAll(searchRequest);
        giftCertificateEntityList.forEach(giftCertificate -> giftCertificate.setTagEntities(tagDAO.findAllByGiftCertificateId(giftCertificate.getId())));
        return giftCertificateEntityList;
    }

    /**
     * Method updates gift certificate.
     * Checks if giftCertificate  is exists by id.
     * Then checks if tags set is null or empty:
     * Creates new or updates existing tags
     * @param giftCertificateEntity candidate for update
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void update(GiftCertificateEntity giftCertificateEntity) {

        if (!giftCertificateDAO.existById(giftCertificateEntity.getId())) {
            throw new GiftCertificateNotFoundException((messageSource.getMessage("giftcertificate.notfound.exception", new Object[]{giftCertificateEntity.getId()},
                    LocaleContextHolder.getLocale())));
        }

        if (giftCertificateEntity.getTagEntities() != null
                && giftCertificateEntity.getTagEntities().size() > 0) {
            giftCertificateTagDAO.deleteByGiftCertificateId(giftCertificateEntity.getId());
            giftCertificateEntity.getTagEntities().forEach(tag -> {

                if (tagDAO.existByName(tag.getName())) {
                    giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagDAO.findByName(tag.getName()).getId()));
                } else {
                    long tagId = tagDAO.create(tag);
                    giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagId));
                }
            });
        }
        giftCertificateDAO.update(giftCertificateEntity);
    }

    /**
     * Method deletes gift certificate
     * Checks if gift certificate exists by id:
     * - if true - deletes from database
     * - if false - throws GiftCertificateNotFoundException exception
     *
     * @param id requested parameter
     */
    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void delete(long id) {

        if (!giftCertificateDAO.existById(id)) {
            throw new GiftCertificateNotFoundException((messageSource.getMessage("giftcertificate.notfound.exception", new Object[]{id},
                    LocaleContextHolder.getLocale())));
        }
        giftCertificateDAO.deleteById(id);
    }
}

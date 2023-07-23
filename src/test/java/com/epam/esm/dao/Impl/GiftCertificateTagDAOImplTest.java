package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.GiftCertificateTagEntity;
import com.epam.esm.entity.TagEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Sql("/table.sql")
@ExtendWith(SpringExtension.class)
class GiftCertificateTagDAOImplTest {
    @Autowired
    GiftCertificateTagDAO giftCertificateTagDAO;

    @Autowired
    TagDAO tagDAO;

    @Autowired
    GiftCertificateDAO giftCertificateDAO;

    @Test
    void create() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName("First tag");
        tagEntity.setId(tagDAO.create(tagEntity));
        GiftCertificateEntity giftCertificateEntity = new GiftCertificateEntity();
        giftCertificateEntity.setName("Gift name");
        giftCertificateEntity.setDuration(2);
        giftCertificateEntity.setPrice(22);
        giftCertificateEntity.setId(giftCertificateDAO.create(giftCertificateEntity));
        giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagEntity.getId()));

        assertEquals(new ArrayList<TagEntity>() {{
            add(tagEntity);
        }}, tagDAO.findAllByGiftCertificateId(giftCertificateEntity.getId()));
    }

    @Test
    void deleteByGiftCertificateId() {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName("First tag");
        tagEntity.setId(tagDAO.create(tagEntity));
        GiftCertificateEntity giftCertificateEntity = new GiftCertificateEntity();
        giftCertificateEntity.setName("Gift name");
        giftCertificateEntity.setDuration(2);
        giftCertificateEntity.setPrice(22);
        giftCertificateEntity.setId(giftCertificateDAO.create(giftCertificateEntity));
        giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagEntity.getId()));
        assertEquals(giftCertificateEntity, giftCertificateDAO.findById(giftCertificateEntity.getId()));
        assertEquals(1,tagDAO.findAllByGiftCertificateId(giftCertificateEntity.getId()).size());
        giftCertificateTagDAO.deleteByGiftCertificateId(giftCertificateEntity.getId());
        assertNull(giftCertificateDAO.findById(giftCertificateEntity.getId()).getTagEntities());
        assertEquals(0,tagDAO.findAllByGiftCertificateId(giftCertificateEntity.getId()).size());
        assertEquals(tagEntity, tagDAO.findById(tagEntity.getId()));
        assertEquals(giftCertificateEntity, giftCertificateDAO.findById(tagEntity.getId()));
    }

    @Test
    void findById() {
        assertThrows(UnsupportedOperationException.class, () -> {
            giftCertificateTagDAO.findById(1L);
        });
    }

    @Test
    void findAll() {
        assertThrows(UnsupportedOperationException.class, () -> {
            giftCertificateTagDAO.findAll();
        });
    }

    @Test
    void deleteById() {
        assertThrows(UnsupportedOperationException.class, () -> {
            giftCertificateTagDAO.deleteById(1L);
        });
    }

    @Test
    void update() {
        assertThrows(UnsupportedOperationException.class, () -> {
            giftCertificateTagDAO.update(new GiftCertificateTagEntity(1L, 1L));
        });
    }


}
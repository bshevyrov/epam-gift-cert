package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
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
@ActiveProfiles("DEV")
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
        Tag tag = new Tag("First tag");
        tag.setId(tagDAO.create(tag));
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Gift name");
        giftCertificate.setDuration(2);
        giftCertificate.setPrice(22);
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tag.getId()));

        assertEquals(new ArrayList<Tag>() {{
            add(tag);
        }}, tagDAO.findAllByGiftCertificateId(giftCertificate.getId()));
    }

    @Test
    void deleteByGiftCertificateId() {
        Tag tag = new Tag("First tag");
        tag.setId(tagDAO.create(tag));
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Gift name");
        giftCertificate.setDuration(2);
        giftCertificate.setPrice(22);
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tag.getId()));
        assertEquals(giftCertificate, giftCertificateDAO.findById(giftCertificate.getId()));
        assertEquals(1,tagDAO.findAllByGiftCertificateId(giftCertificate.getId()).size());
        giftCertificateTagDAO.deleteByGiftCertificateId(giftCertificate.getId());
        assertNull(giftCertificateDAO.findById(giftCertificate.getId()).getTags());
        assertEquals(0,tagDAO.findAllByGiftCertificateId(giftCertificate.getId()).size());
        assertEquals(tag, tagDAO.findById(tag.getId()));
        assertEquals(giftCertificate, giftCertificateDAO.findById(tag.getId()));
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
            giftCertificateTagDAO.update(new GiftCertificateTag(1L, 1L));
        });
    }


}
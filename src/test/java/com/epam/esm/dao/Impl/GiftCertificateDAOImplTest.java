package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("DEV")
@Sql("/table.sql")
@ExtendWith(SpringExtension.class)
class GiftCertificateDAOImplTest {

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    @Autowired
    private TagDAOImpl tagDAO;
    @Autowired
    private GiftCertificateTagDAOImpl giftCertificateTagDAO;

    private GiftCertificate giftCertificate;
    private Tag tagName;
    private Tag tag;


    @BeforeAll
    public void setup() {

        giftCertificate = new GiftCertificate();
        giftCertificate.setName("tes22");
        giftCertificate.setDescription("tes22");
        giftCertificate.setDuration(2);
        giftCertificate.setPrice(2);

        tagName = new Tag("tagName");
        tag = new Tag("tag");

    }


    @Test
    void findById() {

        long createdGiftCertificateId = giftCertificateDAO.create(giftCertificate);
        giftCertificate.setId(createdGiftCertificateId);
        GiftCertificate byId = giftCertificateDAO.findById(createdGiftCertificateId);
        assertEquals(giftCertificate, byId);
    }

    @Test
    void findAll() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> giftCertificateDAO.findAll());
    }

    @Test
    void deleteById() {
        GiftCertificate giftCertificate = new GiftCertificate();
        List<GiftCertificate> expected = new ArrayList<>();
        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        assertEquals(giftCertificate, giftCertificateDAO.findById(giftCertificate.getId()));
        expected.add(giftCertificate);

        giftCertificate = new GiftCertificate();
        giftCertificate.setName("second");
        long secondId = giftCertificateDAO.create(giftCertificate);
        giftCertificate.setId(secondId);
        assertEquals(giftCertificate, giftCertificateDAO.findById(secondId));

        giftCertificate = new GiftCertificate();
        giftCertificate.setName("third");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        assertEquals(giftCertificate, giftCertificateDAO.findById(giftCertificate.getId()));
        expected.add(giftCertificate);

        giftCertificateDAO.deleteById(secondId);
        assertThrows(Exception.class, () -> giftCertificateDAO.findById(secondId));
    }


    @Test
    void update() {
        GiftCertificate giftCertificateUpdate = new GiftCertificate();
        giftCertificateUpdate.setDuration(2);
        giftCertificateUpdate.setPrice(2);
        giftCertificateUpdate.setName("first");
        giftCertificateUpdate.setId(giftCertificateDAO.create(giftCertificateUpdate));
        giftCertificateUpdate.setName("second");
        giftCertificateDAO.update(giftCertificateUpdate);
        assertEquals(giftCertificateUpdate, giftCertificateDAO.findById(giftCertificateUpdate.getId()));

    }

}
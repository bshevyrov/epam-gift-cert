package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Assertions;
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


@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("DEV")
@Sql("/table.sql")
@ExtendWith(SpringExtension.class)
class GiftCertificateDAOImplTest {

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    private GiftCertificate giftCertificate;


    @BeforeAll
    public void setup() {

        giftCertificate = new GiftCertificate();
        giftCertificate.setName("tes22");
        giftCertificate.setDescription("tes22");
        giftCertificate.setDuration(2);
        giftCertificate.setPrice(2);
    }


    @Test
    void findById() {

        long createdGiftCertificateId = giftCertificateDAO.create(giftCertificate);
        giftCertificate.setId(createdGiftCertificateId);
        GiftCertificate byId = giftCertificateDAO.findById(createdGiftCertificateId);
        Assertions.assertEquals(giftCertificate, byId);
    }

    @Test
    void findAll() {
        Assertions.assertThrowsExactly(UnsupportedOperationException.class, () -> giftCertificateDAO.findAll());
    }

    @Test
    void deleteById() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        Assertions.assertEquals(giftCertificate, giftCertificateDAO.findById(giftCertificate.getId()));

        giftCertificate = new GiftCertificate();
        giftCertificate.setName("second");
        long secondId = giftCertificateDAO.create(giftCertificate);
        giftCertificate.setId(secondId);
        Assertions.assertEquals(giftCertificate, giftCertificateDAO.findById(secondId));

        giftCertificate = new GiftCertificate();
        giftCertificate.setName("third");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        Assertions.assertEquals(giftCertificate, giftCertificateDAO.findById(giftCertificate.getId()));

        giftCertificateDAO.deleteById(secondId);
        Assertions.assertThrows(Exception.class, () -> giftCertificateDAO.findById(secondId));

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
        Assertions.assertEquals(giftCertificateUpdate, giftCertificateDAO.findById(giftCertificateUpdate.getId()));

    }

}
package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificateEntity;
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
@ActiveProfiles("test")
@Sql("/table.sql")
@ExtendWith(SpringExtension.class)
class GiftCertificateDAOImplTest {

    @Autowired
    private GiftCertificateDAO giftCertificateDAO;

    private GiftCertificateEntity giftCertificateEntity;


    @BeforeAll
    public void setup() {

        giftCertificateEntity = new GiftCertificateEntity();
        giftCertificateEntity.setName("tes22");
        giftCertificateEntity.setDescription("tes22");
        giftCertificateEntity.setDuration(2);
        giftCertificateEntity.setPrice(2);
    }


    @Test
    void findById() {

        long createdGiftCertificateId = giftCertificateDAO.create(giftCertificateEntity);
        giftCertificateEntity.setId(createdGiftCertificateId);
        GiftCertificateEntity byId = giftCertificateDAO.findById(createdGiftCertificateId);
        Assertions.assertEquals(giftCertificateEntity, byId);
    }

    @Test
    void findAll() {
        Assertions.assertThrowsExactly(UnsupportedOperationException.class, () -> giftCertificateDAO.findAll());
    }

    @Test
    void deleteById() {
        GiftCertificateEntity giftCertificateEntity = new GiftCertificateEntity();
        giftCertificateEntity.setName("first");
        giftCertificateEntity.setId(giftCertificateDAO.create(giftCertificateEntity));
        Assertions.assertEquals(giftCertificateEntity, giftCertificateDAO.findById(giftCertificateEntity.getId()));

        giftCertificateEntity = new GiftCertificateEntity();
        giftCertificateEntity.setName("second");
        long secondId = giftCertificateDAO.create(giftCertificateEntity);
        giftCertificateEntity.setId(secondId);
        Assertions.assertEquals(giftCertificateEntity, giftCertificateDAO.findById(secondId));

        giftCertificateEntity = new GiftCertificateEntity();
        giftCertificateEntity.setName("third");
        giftCertificateEntity.setId(giftCertificateDAO.create(giftCertificateEntity));
        Assertions.assertEquals(giftCertificateEntity, giftCertificateDAO.findById(giftCertificateEntity.getId()));

        giftCertificateDAO.deleteById(secondId);
        Assertions.assertThrows(Exception.class, () -> giftCertificateDAO.findById(secondId));

    }


    @Test
    void update() {
        GiftCertificateEntity giftCertificateEntityUpdate = new GiftCertificateEntity();
        giftCertificateEntityUpdate.setDuration(2);
        giftCertificateEntityUpdate.setPrice(2);
        giftCertificateEntityUpdate.setName("first");
        giftCertificateEntityUpdate.setId(giftCertificateDAO.create(giftCertificateEntityUpdate));
        giftCertificateEntityUpdate.setName("second");
        giftCertificateDAO.update(giftCertificateEntityUpdate);
        Assertions.assertEquals(giftCertificateEntityUpdate, giftCertificateDAO.findById(giftCertificateEntityUpdate.getId()));

    }

}
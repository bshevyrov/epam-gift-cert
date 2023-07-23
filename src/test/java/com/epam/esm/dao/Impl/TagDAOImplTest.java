package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.GiftCertificateTagEntity;
import com.epam.esm.entity.TagEntity;
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

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("DEV")
@Sql("/table.sql")
@ExtendWith(SpringExtension.class)
class TagDAOImplTest {
    @Autowired
    TagDAO tagDAO;
    @Autowired
    GiftCertificateDAO giftCertificateDAO;
    @Autowired
    GiftCertificateTagDAO giftCertificateTagDAO;
    TagEntity tagEntityOne;
    TagEntity tagEntityTwo;
    TagEntity tagEntityThree;

    @BeforeAll
    public void setup() {
        tagEntityOne = new TagEntity();
        tagEntityOne.setName("First tag");
        tagEntityTwo = new TagEntity();
        tagEntityOne.setName("Second tag");
        tagEntityThree = new TagEntity();
        tagEntityOne.setName("Third tag");

    }

    @Test
    void createAndFindByIdAndFindAll() {
        assertEquals(0, tagDAO.findAll().size());
        tagEntityOne.setId(tagDAO.create(tagEntityOne));
        assertEquals(1, tagDAO.findAll().size());
        assertEquals(tagEntityOne, tagDAO.findById(tagEntityOne.getId()));
        tagEntityTwo.setId(tagDAO.create(tagEntityTwo));
        tagEntityThree.setId(tagDAO.create(tagEntityThree));
        assertNotEquals(tagEntityOne.getId(), tagEntityTwo.getId());
        assertNotEquals(tagEntityOne.getId(), tagEntityThree.getId());
        assertEquals(3, tagDAO.findAll().size());
    }

    @Test
    void deleteById() {
        tagEntityOne.setId(tagDAO.create(tagEntityOne));
        tagEntityTwo.setId(tagDAO.create(tagEntityTwo));
        tagEntityThree.setId(tagDAO.create(tagEntityThree));
        assertEquals(3, tagDAO.findAll().size());
        tagDAO.deleteById(tagEntityTwo.getId());
        assertEquals(2, tagDAO.findAll().size());
        assertEquals(new ArrayList<TagEntity>() {{
            add(tagEntityOne);
            add(tagEntityThree);
        }}, tagDAO.findAll());
    }

    @Test
    void update() {
        tagEntityOne.setId(tagDAO.create(tagEntityOne));
        tagEntityTwo.setId(tagDAO.create(tagEntityTwo));
        tagEntityThree.setId(tagDAO.create(tagEntityThree));

        TagEntity newTagEntity = new TagEntity();
        newTagEntity.setName("new Tag");
        newTagEntity.setId(tagDAO.create(newTagEntity));
        newTagEntity.setName("update name");

        assertThrows(UnsupportedOperationException.class, () -> {
            tagDAO.update(newTagEntity);
        });
        assertNotEquals(newTagEntity, tagDAO.findById(newTagEntity.getId()));
        assertEquals(4, tagDAO.findAll().size());
    }

    @Test
    void findAllByGiftCertificateId() {
        tagEntityOne.setId(tagDAO.create(tagEntityOne));
        tagEntityTwo.setId(tagDAO.create(tagEntityTwo));
        tagEntityThree.setId(tagDAO.create(tagEntityThree));

        GiftCertificateEntity giftCertificateEntity = new GiftCertificateEntity();
        giftCertificateEntity.setName("First gift");
        giftCertificateEntity.setPrice(66);
        giftCertificateEntity.setDuration(1);
        giftCertificateEntity.setId(giftCertificateDAO.create(giftCertificateEntity));

        GiftCertificateEntity giftCertificateEntityTwo = new GiftCertificateEntity();
        giftCertificateEntityTwo.setName("Second gift");
        giftCertificateEntityTwo.setPrice(77);
        giftCertificateEntityTwo.setDuration(2);
        giftCertificateEntityTwo.setId(giftCertificateDAO.create(giftCertificateEntityTwo));

        giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagEntityOne.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntity.getId(), tagEntityTwo.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntityTwo.getId(), tagEntityTwo.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTagEntity(giftCertificateEntityTwo.getId(), tagEntityThree.getId()));
        assertEquals(new ArrayList<TagEntity>() {{
            add(tagEntityOne);
            add(tagEntityTwo);
        }}, tagDAO.findAllByGiftCertificateId(giftCertificateEntity.getId()));
    }

    @Test
    void existByName() {
        tagEntityThree.setId(tagDAO.create(tagEntityThree));
        tagEntityOne.setId(tagDAO.create(tagEntityOne));
        tagEntityTwo.setId(tagDAO.create(tagEntityTwo));
        assertTrue(tagDAO.existByName(tagEntityThree.getName()));
        assertFalse(tagDAO.existByName("new Name"));
    }

    @Test
    void findByName() {
        tagEntityOne.setId(tagDAO.create(tagEntityOne));
        tagEntityTwo.setId(tagDAO.create(tagEntityTwo));
        tagEntityThree.setId(tagDAO.create(tagEntityThree));
        assertEquals(tagEntityThree, tagDAO.findByName(tagEntityThree.getName()));
    }
}
package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
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
    Tag tagOne;
    Tag tagTwo;
    Tag tagThree;

    @BeforeAll
    public void setup() {
        tagOne = new Tag();
        tagOne.setName("First tag");
        tagTwo = new Tag();
        tagOne.setName("Second tag");
        tagThree = new Tag();
        tagOne.setName("Third tag");

    }

    @Test
    void createAndFindByIdAndFindAll() {
        assertEquals(0, tagDAO.findAll().size());
        tagOne.setId(tagDAO.create(tagOne));
        assertEquals(1, tagDAO.findAll().size());
        assertEquals(tagOne, tagDAO.findById(tagOne.getId()));
        tagTwo.setId(tagDAO.create(tagTwo));
        tagThree.setId(tagDAO.create(tagThree));
        assertNotEquals(tagOne.getId(), tagTwo.getId());
        assertNotEquals(tagOne.getId(), tagThree.getId());
        assertEquals(3, tagDAO.findAll().size());
    }

    @Test
    void deleteById() {
        tagOne.setId(tagDAO.create(tagOne));
        tagTwo.setId(tagDAO.create(tagTwo));
        tagThree.setId(tagDAO.create(tagThree));
        assertEquals(3, tagDAO.findAll().size());
        tagDAO.deleteById(tagTwo.getId());
        assertEquals(2, tagDAO.findAll().size());
        assertEquals(new ArrayList<Tag>() {{
            add(tagOne);
            add(tagThree);
        }}, tagDAO.findAll());
    }

    @Test
    void update() {
        tagOne.setId(tagDAO.create(tagOne));
        tagTwo.setId(tagDAO.create(tagTwo));
        tagThree.setId(tagDAO.create(tagThree));

        Tag newTag = new Tag();
        newTag.setName("new Tag");
        newTag.setId(tagDAO.create(newTag));
        newTag.setName("update name");

        assertThrows(UnsupportedOperationException.class, () -> {
            tagDAO.update(newTag);
        });
        assertNotEquals(newTag, tagDAO.findById(newTag.getId()));
        assertEquals(4, tagDAO.findAll().size());
    }

    @Test
    void findAllByGiftCertificateId() {
        tagOne.setId(tagDAO.create(tagOne));
        tagTwo.setId(tagDAO.create(tagTwo));
        tagThree.setId(tagDAO.create(tagThree));

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("First gift");
        giftCertificate.setPrice(66);
        giftCertificate.setDuration(1);
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));

        GiftCertificate giftCertificateTwo = new GiftCertificate();
        giftCertificateTwo.setName("Second gift");
        giftCertificateTwo.setPrice(77);
        giftCertificateTwo.setDuration(2);
        giftCertificateTwo.setId(giftCertificateDAO.create(giftCertificateTwo));

        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagOne.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagTwo.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificateTwo.getId(), tagTwo.getId()));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificateTwo.getId(), tagThree.getId()));
        assertEquals(new ArrayList<Tag>() {{
            add(tagOne);
            add(tagTwo);
        }}, tagDAO.findAllByGiftCertificateId(giftCertificate.getId()));
    }

    @Test
    void existByName() {
        tagThree.setId(tagDAO.create(tagThree));
        tagOne.setId(tagDAO.create(tagOne));
        tagTwo.setId(tagDAO.create(tagTwo));
        assertTrue(tagDAO.existByName(tagThree.getName()));
        assertFalse(tagDAO.existByName("new Name"));
    }

    @Test
    void findByName() {
        tagOne.setId(tagDAO.create(tagOne));
        tagTwo.setId(tagDAO.create(tagTwo));
        tagThree.setId(tagDAO.create(tagThree));
        assertEquals(tagThree, tagDAO.findByName(tagThree.getName()));
    }
}
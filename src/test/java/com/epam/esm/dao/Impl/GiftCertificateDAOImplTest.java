package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
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

        List<GiftCertificate> actual = giftCertificateDAO.findAll();
        assertEquals(0, actual.size());

        giftCertificateDAO.create(giftCertificate);
        giftCertificateDAO.create(giftCertificate);
        giftCertificateDAO.create(giftCertificate);
        actual = giftCertificateDAO.findAll();

        assertEquals(3, actual.size());
    }

    @Test
    void testFindAll() {

    }

    @Test
    void deleteById() {
        GiftCertificate giftCertificate = new GiftCertificate();
        List<GiftCertificate> expected = new ArrayList<>();
        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));

        giftCertificate.setName("second");
        long secondId = giftCertificateDAO.create(giftCertificate);

        giftCertificate.setName("third");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));

        giftCertificateDAO.deleteById(secondId);
        assertEquals(expected, giftCertificateDAO.findAll());
        assertEquals(expected.size(), giftCertificateDAO.findAll().size());
    }


    @Test
    void findByTagName() {
        List<GiftCertificate> expected = new ArrayList<>();

        long tagId = tagDAO.create(tag);
        tag.setId(tagId);
        long tagNameId = tagDAO.create(tagName);
        tagName.setId(tagNameId);

        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));
        giftCertificateDAO.create(giftCertificate);
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagNameId));

        giftCertificate.setName("second");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        expected.add(SerializationUtils.clone(giftCertificate));
        giftCertificateDAO.create(giftCertificate);
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagNameId));
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagId));

        giftCertificate.setName("third");
        giftCertificate.setTags(new ArrayList<Tag>() {{
            add(tag);
        }});
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));
        giftCertificateDAO.create(giftCertificate);
        giftCertificateTagDAO.create(new GiftCertificateTag(giftCertificate.getId(), tagId));

        assertEquals(2, giftCertificateDAO.findAllByTagName("tagName").size());
        assertEquals(expected, giftCertificateDAO.findAllByTagName("tagName"));
    }

    @Test
    void existById() {
        giftCertificate.setName("first");
        giftCertificate.setId(giftCertificateDAO.create(giftCertificate));

        assertTrue(giftCertificateDAO.existById(giftCertificate.getId()));
        assertFalse(giftCertificateDAO.existById(giftCertificate.getId() + 1));
    }


    @Test
    void update() {
        GiftCertificate giftCertificateUpdate = ObjectUtils.clone(giftCertificate);
        giftCertificateUpdate.setName("first");
        giftCertificateUpdate.setId(giftCertificateDAO.create(giftCertificateUpdate));
        giftCertificateUpdate.setName("second");
        giftCertificateDAO.update(giftCertificateUpdate);
        assertEquals(giftCertificateUpdate, giftCertificateDAO.findById(giftCertificateUpdate.getId()));

    }

}
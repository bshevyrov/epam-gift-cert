package com.epam.esm.dao.Impl;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.TagDAO;
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
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    Tag tagOne;
    Tag tagTwo;
    Tag tagThree;

    @BeforeAll
    public void setup() {
        tagOne = new Tag("First tag");
        tagTwo = new Tag("Second tag");
        tagThree = new Tag("Third tag");
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
    @ExceptionHandler(UnsupportedOperationException.class)
    void update() {
        tagOne.setId(tagDAO.create(tagOne));
        tagTwo.setId(tagDAO.create(tagTwo));
        tagThree.setId(tagDAO.create(tagThree));

        tagThree.setName("new Name");

        assertThrows(UnsupportedOperationException.class, () -> {
            tagDAO.update(tagThree);
        });
        assertNotEquals(tagThree, tagDAO.findById(tagThree.getId()));
        assertEquals(3, tagDAO.findAll().size());


    }

    @Test
    void findAllByGiftCertificateId() {
    }

    @Test
    void existByName() {
        tagOne.setId(tagDAO.create(tagOne));
        tagTwo.setId(tagDAO.create(tagTwo));
        tagThree.setId(tagDAO.create(tagThree));
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
package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateIdException;
import com.epam.esm.exception.TagIdException;
import com.epam.esm.exception.TagNameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceImplTest {
    @Mock
    private TagDAO tagDAO;

    @InjectMocks
    private TagServiceImpl tagService;

    private Map<String, Object> updateMap;

    @BeforeAll
    public void initTagTest() {
        MockitoAnnotations.openMocks(this);
        new HashMap<String, Object>() {{
            put("name", "newName");
        }};

    }

    @ParameterizedTest(name = "tagName is  - {0}")
    @ValueSource(strings = {"1", " ", "", "a ", "a1"})
    void throwsExceptionWhenCreateNameNotAlphanumericName(String tagName) {
        Assertions.assertThrowsExactly(TagNameException.class, () -> tagService.create(new Tag(tagName)));
    }

    @ParameterizedTest(name = "tagId id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE, Long.MAX_VALUE + 1})
    void throwsExceptionWhenTagFindByIdIdLessOne(long tagId) {
        Assertions.assertThrowsExactly(TagIdException.class, () -> tagService.findById(tagId));
    }

    @Test
    void findAll() {
        tagService.findAll();
        Mockito.verify(tagDAO, Mockito.times(1)).findAll();
    }

    @Test
    void update() {
        tagService.update(updateMap);
        Mockito.verify(tagDAO, Mockito.times(1)).update(updateMap);
    }

    @ParameterizedTest(name = "tagId id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE, Long.MAX_VALUE + 1})
    void throwsExceptionWhenDeleteTagIdLessOne(long tagId) {
        Assertions.assertThrowsExactly(TagIdException.class, () -> tagService.delete(tagId));
    }

    @ParameterizedTest(name = "giftCertificate id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE, Long.MAX_VALUE + 1})
    void throwsExceptionWhenFindAllByGSIdLessOne(long giftCertificateId) {
        Assertions.assertThrowsExactly(GiftCertificateIdException.class, () -> tagService.findAllByGiftCertificateId(giftCertificateId));
    }

    @ParameterizedTest(name = "tagName is  - {0}")
    @ValueSource(strings = {"1", " ", "", "a ", "a1"})
    void throwsExceptionWhenExistByNameNameNotAlphanumericName(String tagName) {
        Assertions.assertThrowsExactly(TagNameException.class, () -> tagService.existByName(tagName));
    }
}
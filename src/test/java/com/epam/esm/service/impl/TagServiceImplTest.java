package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.tag.TagIdException;
import com.epam.esm.exception.tag.TagNameException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagServiceImplTest {
    @Mock
    private TagDAO tagDAO;

    @InjectMocks
    private TagServiceImpl tagService;


    @BeforeAll
    public void initTagTest() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest(name = "tagName is  - {0}")
    @ValueSource(strings = {"1", " ", "", "a ", "a1"})
    void throwsExceptionWhenCreateNameNotAlphaName(String tagName) {
        Tag tag = new Tag();
        tag.setName(tagName);
        assertThrowsExactly(TagNameException.class, () -> tagService.create(tag));
    }

    @Test
    void throwsExceptionWhenTagFindByIdIdLessOne() {
        assertThrowsExactly(TagIdException.class, () -> tagService.findById(0));
    }

    @Test
    void findAll() {
        tagService.findAll();
        Mockito.verify(tagDAO, Mockito.times(1)).findAll();
    }

    @Test
    void update() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> tagService.update(any()));
    }


    @ParameterizedTest(name = "tagId id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE, Long.MAX_VALUE + 1})
    void throwsExceptionWhenDeleteTagIdLessOne(long tagId) {
        assertThrowsExactly(TagIdException.class, () -> tagService.delete(tagId));
    }

    @ParameterizedTest(name = "giftCertificate id - {0}")
    @ValueSource(longs = {0L, -1L, Long.MIN_VALUE, Long.MAX_VALUE + 1})
    void throwsExceptionWhenFindAllByGCIdLessOne(long giftCertificateId) {
        assertThrowsExactly(GiftCertificateIdException.class, () -> tagService.findAllByGiftCertificateId(giftCertificateId));
    }

}
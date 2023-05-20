package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateIdException;
import com.epam.esm.exception.GiftCertificateNotFoundException;
import com.epam.esm.exception.TagNameException;
import com.epam.esm.util.InputVerification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private GiftCertificateTagDAO giftCertificateTagDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private Tag tag;


    @Mock
    private GiftCertificate giftCertificate;
    private List<Tag> tags;

    private Map<String, Object> withIdLessZero;
    private Map<String, Object> withIdBiggerZero;
    private Map<String, Object> withOutId;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);

        tags = new ArrayList<Tag>() {{
            add(new Tag("11"));
        }};
        withIdLessZero = new HashMap<String, Object>() {{
            put("id", -1L);
        }};
        withOutId = new HashMap<String, Object>() {{
            put("ids", 1L);
        }};
        withIdBiggerZero = new HashMap<String, Object>() {{
            put("id", 1L);
        }};
    }

    @ParameterizedTest(name = "Tag is exist - {0} ")
    @ValueSource(booleans = {true, false})
    void whenCreateGiftCertificateWithOldTagThanNoTagCreate(boolean tagExist) {
        try (MockedStatic<InputVerification> utilities = mockStatic(InputVerification.class)) {
            utilities.when(() ->
                    InputVerification.verifyName(anyString())).thenReturn(true);

            when(tagDAO.existByName(anyString())).thenReturn(tagExist);
            when(giftCertificateTagDAO.create(any(GiftCertificateTag.class))).thenReturn(1L);
            when(tagDAO.findByName(anyString())).thenReturn(tag);
            when(tag.getId()).thenReturn(2L);
            when(giftCertificate.getTags()).thenReturn(tags);

            giftCertificateService.create(giftCertificate);
            verify(tagDAO, never()).create(any(Tag.class));
        }
    }

    @ParameterizedTest(name = "Tag exist - {0} ")
    @ValueSource(booleans = {true, false})
    void whenCreateGiftCertificateWithNewTagThanTagCreate(boolean tagExist) {
        try (MockedStatic<InputVerification> utilities = mockStatic(InputVerification.class)) {
            utilities.when(() ->
                    InputVerification.verifyName(anyString())).thenReturn(true);

            when(tagDAO.existByName(anyString())).thenReturn(tagExist);
            when(giftCertificateTagDAO.create(any(GiftCertificateTag.class))).thenReturn(1L);
            when(tagDAO.findByName(anyString())).thenReturn(new Tag());
            when(tag.getId()).thenReturn(10L);
            when(giftCertificate.getTags()).thenReturn(tags);
            giftCertificateService.create(giftCertificate);
            verify(tagDAO, times(1)).create(any(Tag.class));
        }
    }

    @ParameterizedTest(name = "gift certificate id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE})
    void throwsExceptionWhenGiftCertificateFindByIdIdLessOne(long giftCertificateId) {
        assertThrowsExactly(GiftCertificateIdException.class, () -> giftCertificateService.findById(giftCertificateId));
    }


    @Test
    void throwsExceptionWhenGiftCertificateUpdateIdLessOne() {
        assertThrowsExactly(GiftCertificateIdException.class, () -> giftCertificateService.update(withIdLessZero));
    }

    @Test
    void throwsGiftCertificateNotFoundExceptionWhenGiftCertificateUpdateIdBiggerZero() {
        when(giftCertificateDAO.existById(anyLong())).thenReturn(false);
        assertThrowsExactly(GiftCertificateNotFoundException.class, () -> giftCertificateService.update(withIdBiggerZero));
    }

    @ParameterizedTest(name = "gift certificate id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE})
    void throwsExceptionWhenGiftCertificateDeleteByIdIdLessOne(long giftCertificateId) {
        assertThrowsExactly(GiftCertificateIdException.class, () -> giftCertificateService.delete(giftCertificateId));
    }

    @ParameterizedTest(name = "gift certificate name is  - {0}")
    @ValueSource(strings = {"1", " ", "", "a ", "a1"})
    void throwsExceptionWhenGiftCertFindByTagNameNameNotAlphaName(String tagName) {
        assertThrowsExactly(TagNameException.class, () -> giftCertificateService.findByTagName(tagName));
    }

    @Test
    void whenFindAllWithParametersThenUnsupportedOperationException() {
        assertThrowsExactly(UnsupportedOperationException.class, () -> giftCertificateService.findAll());
    }

    @Test
    void findAll() {
        giftCertificateService.findAll(any(), any(), anyString(), anyString());
        verify(giftCertificateDAO, times(1)).findAll(any(), any(), anyString(), anyString());
    }
}
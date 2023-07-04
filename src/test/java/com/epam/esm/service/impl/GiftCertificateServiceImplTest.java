package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.giftcertificate.GiftCertificateNotFoundException;
import com.epam.esm.util.InputVerification;
import com.epam.esm.veiw.SearchRequest;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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


    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);

        tags = new ArrayList<Tag>() {{
            add(new Tag("11"));
        }};

    }

    @Test
    void whenCreateGiftCertificateWithOldTagThanNoTagCreate() {
        try (MockedStatic<InputVerification> utilities = mockStatic(InputVerification.class)) {
            utilities.when(() ->
                    InputVerification.verifyName(anyString())).thenReturn(true);
            Tag tag2 = new Tag("two");
            when(tagDAO.existByName(tag2.getName())).thenReturn(true);
            when(giftCertificateTagDAO.create(any(GiftCertificateTag.class))).thenReturn(1L);
            when(tagDAO.findByName(tag2.getName())).thenReturn(tag2);
            when(tag.getId()).thenReturn(2L);
            when(giftCertificate.getTags()).thenReturn(Arrays.asList(tag2));

            giftCertificateService.create(giftCertificate);
            verify(tagDAO, never()).create(tag2);
        }
    }

    @Test
    void whenCreateGiftCertificateWithNewTagThanTagCreate() {
        try (MockedStatic<InputVerification> utilities = mockStatic(InputVerification.class)) {
            utilities.when(() ->
                    InputVerification.verifyName(anyString())).thenReturn(true);

            Tag tag1 = new Tag("one");
            when(tagDAO.existByName(tag1.getName())).thenReturn(false);
            when(giftCertificateTagDAO.create(any(GiftCertificateTag.class))).thenReturn(1L);
            when(tagDAO.findByName(anyString())).thenReturn(tag1);
            when(tag.getId()).thenReturn(10L);
            when(giftCertificate.getTags()).thenReturn(Arrays.asList(tag1));
            giftCertificateService.create(giftCertificate);
            verify(tagDAO, times(1)).create(tag1);
        }
    }

    @ParameterizedTest(name = "gift certificate id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE})
    void throwsExceptionWhenGiftCertificateFindByIdIdLessOne(long giftCertificateId) {
        assertThrowsExactly(GiftCertificateIdException.class, () -> giftCertificateService.findById(giftCertificateId));
    }


    @Test
    void throwsExceptionWhenGiftCertificateUpdateIdLessOne() {
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setId(-1);
        assertThrowsExactly(GiftCertificateIdException.class, () -> giftCertificateService.update(giftCertificate1));
    }


    @Test
    void throwsGiftCertificateNotFoundExceptionWhenGiftCertificateUpdateIdBiggerZero() {
        GiftCertificate giftCertificate1 = new GiftCertificate();
        giftCertificate1.setId(1);
        when(giftCertificateDAO.existById(anyLong())).thenReturn(false);
        assertThrowsExactly(GiftCertificateNotFoundException.class, () -> giftCertificateService.update(giftCertificate1));
    }

    @ParameterizedTest(name = "gift certificate id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE})
    void throwsExceptionWhenGiftCertificateDeleteByIdIdLessOne(long giftCertificateId) {
        assertThrowsExactly(GiftCertificateIdException.class, () -> giftCertificateService.delete(giftCertificateId));
    }


    @Test
    void findAll() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setDescription(Optional.of("22"));
        giftCertificateService.findAll(searchRequest);
        verify(giftCertificateDAO, times(1)).findAll(searchRequest);
    }
}
package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.GiftCertificateTagEntity;
import com.epam.esm.entity.TagEntity;
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

import java.util.Arrays;
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
    private TagEntity tagEntity;


    @Mock
    private GiftCertificateEntity giftCertificateEntity;


    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenCreateGiftCertificateWithOldTagThanNoTagCreate() {
        try (MockedStatic<InputVerification> utilities = mockStatic(InputVerification.class)) {
            utilities.when(() ->
                    InputVerification.verifyName(anyString())).thenReturn(true);
            TagEntity tagEntity2 = new TagEntity();
            tagEntity2.setName("two");
            when(tagDAO.existByName(tagEntity2.getName())).thenReturn(true);
            when(giftCertificateTagDAO.create(any(GiftCertificateTagEntity.class))).thenReturn(1L);
            when(tagDAO.findByName(tagEntity2.getName())).thenReturn(tagEntity2);
            when(tagEntity.getId()).thenReturn(2L);
            when(giftCertificateEntity.getTagEntities()).thenReturn(Arrays.asList(tagEntity2));

            giftCertificateService.create(giftCertificateEntity);
            verify(tagDAO, never()).create(tagEntity2);
        }
    }

    @Test
    void whenCreateGiftCertificateWithNewTagThanTagCreate() {
        try (MockedStatic<InputVerification> utilities = mockStatic(InputVerification.class)) {
            utilities.when(() ->
                    InputVerification.verifyName(anyString())).thenReturn(true);

            TagEntity tagEntity1 = new TagEntity();
            tagEntity1.setName("one");
            when(tagDAO.existByName(tagEntity1.getName())).thenReturn(false);
            when(giftCertificateTagDAO.create(any(GiftCertificateTagEntity.class))).thenReturn(1L);
            when(tagDAO.findByName(anyString())).thenReturn(tagEntity1);
            when(tagEntity.getId()).thenReturn(10L);
            when(giftCertificateEntity.getTagEntities()).thenReturn(Arrays.asList(tagEntity1));
            giftCertificateService.create(giftCertificateEntity);
            verify(tagDAO, times(1)).create(tagEntity1);
        }
    }

    @ParameterizedTest(name = "gift certificate id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE})
    void throwsExceptionWhenGiftCertificateFindByIdIdLessOne(long giftCertificateId) {
        assertThrowsExactly(GiftCertificateInvalidIdException.class, () -> giftCertificateService.findById(giftCertificateId));
    }


    @Test
    void throwsExceptionWhenGiftCertificateUpdateIdLessOne() {
        GiftCertificateEntity giftCertificateEntity1 = new GiftCertificateEntity();
        giftCertificateEntity1.setId(-1);
        assertThrowsExactly(GiftCertificateInvalidIdException.class, () -> giftCertificateService.update(giftCertificateEntity1));
    }


    @Test
    void throwsGiftCertificateNotFoundExceptionWhenGiftCertificateUpdateIdBiggerZero() {
        GiftCertificateEntity giftCertificateEntity1 = new GiftCertificateEntity();
        giftCertificateEntity1.setId(1);
        when(giftCertificateDAO.existById(anyLong())).thenReturn(false);
        assertThrowsExactly(GiftCertificateNotFoundException.class, () -> giftCertificateService.update(giftCertificateEntity1));
    }

    @ParameterizedTest(name = "gift certificate id - {0}")
    @ValueSource(longs = {0, -1, Long.MIN_VALUE})
    void throwsExceptionWhenGiftCertificateDeleteByIdIdLessOne(long giftCertificateId) {
        assertThrowsExactly(GiftCertificateInvalidIdException.class, () -> giftCertificateService.delete(giftCertificateId));
    }


    @Test
    void findAll() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setDescription(Optional.of("22"));
        giftCertificateService.findAll(searchRequest);
        verify(giftCertificateDAO, times(1)).findAll(searchRequest);
    }
}
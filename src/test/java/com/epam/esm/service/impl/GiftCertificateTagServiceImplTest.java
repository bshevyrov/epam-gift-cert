package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificateTagEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateTagServiceImplTest {

    @InjectMocks
    private GiftCertificateTagServiceImpl giftCertificateTagService;

    @Mock
    private GiftCertificateTagDAO giftCertificateTagDAO;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void throwsExceptionWhenGiftCertificateTagCreateGiftCertificateIdLessOne() {
        long giftCertificateId = 0L;
        long tagId = 1L;
        GiftCertificateTagEntity giftCertificateTagEntity = new GiftCertificateTagEntity(giftCertificateId, tagId);
        assertThrowsExactly(GiftCertificateInvalidIdException.class, () -> giftCertificateTagService.create(giftCertificateTagEntity));
    }

    @Test
    void throwsExceptionWhenGiftCertificateTagCreateTagLessOne() {
        long giftCertificateId = 1L;
        long tagId = 0L;
        GiftCertificateTagEntity giftCertificateTagEntity = new GiftCertificateTagEntity(giftCertificateId, tagId);
        assertThrowsExactly(TagInvalidIdException.class, () -> giftCertificateTagService.create(giftCertificateTagEntity));
    }

    @Test
    void whenAllIdsOkThenOk() {
        long giftCertificateId = 1L;
        long tagId = 1L;
        GiftCertificateTagEntity giftCertificateTagEntity = new GiftCertificateTagEntity(giftCertificateId, tagId);
        when(tagDAO.existById(tagId)).thenReturn(true);
        when(giftCertificateDAO.existById(giftCertificateId)).thenReturn(true);
        giftCertificateTagService.create(giftCertificateTagEntity);
        verify(giftCertificateTagDAO, times(1)).create(giftCertificateTagEntity);
    }

    @Test
    void update() {
        assertThrows(UnsupportedOperationException.class,  ()->giftCertificateTagService.update(new GiftCertificateTagEntity(0,0)));
    }

    @Test
    void delete() {
        assertThrows(UnsupportedOperationException.class, () ->giftCertificateTagService.delete(-1L));

    }

    @Test
    void findAll() {
        assertThrowsExactly(UnsupportedOperationException.class, () ->giftCertificateTagService.findAll());
    }

    @Test
    void findById() {
   assertThrows(UnsupportedOperationException.class, ()->  giftCertificateTagService.findById(-2L));
    }

}
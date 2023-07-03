package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.exception.giftcertificate.GiftCertificateIdException;
import com.epam.esm.exception.tag.TagIdException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateTagServiceImplTest {

    @InjectMocks
    private GiftCertificateTagServiceImpl giftCertificateTagService;

    @Mock
    private GiftCertificateTagDAO giftCertificateTagDAO;

    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void throwsExceptionWhenGiftCertificateTagCreateGiftCertificateIdLessOne() {
        long giftCertificateId = 0L;
        long tagId = 1L;
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificateId, tagId);
        assertThrowsExactly(GiftCertificateIdException.class, () -> giftCertificateTagService.create(giftCertificateTag));
    }

    @Test
    void throwsExceptionWhenGiftCertificateTagCreateTagLessOne() {
        long giftCertificateId = 1L;
        long tagId = 0L;
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificateId, tagId);
        assertThrowsExactly(TagIdException.class, () -> giftCertificateTagService.create(giftCertificateTag));
    }

    @Test
    void whenAllIdsOkThenOk() {
        long giftCertificateId = 1L;
        long tagId = 1L;
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag(giftCertificateId, tagId);
        giftCertificateTagService.create(giftCertificateTag);
        verify(giftCertificateTagDAO, times(1)).create(giftCertificateTag);
    }
//TODO
/*    @Test
    void update() {
        giftCertificateTagService.update(anyMap());
        verify(giftCertificateTagDAO, Mockito.times(1)).update(anyMap());
    }*/

    @Test
    void delete() {
        giftCertificateTagService.delete(anyLong());
        verify(giftCertificateTagDAO, Mockito.times(1)).deleteById(anyLong());
    }

    @Test
    void findAll() {
        giftCertificateTagService.findAll(tagName, giftCertificateName, description, sortField, sortType);
        verify(giftCertificateTagDAO, Mockito.times(1)).findAll();
    }

    @Test
    void findById() {
        giftCertificateTagService.findById(anyLong());
        verify(giftCertificateTagDAO, Mockito.times(1)).findById(anyLong());
    }

}
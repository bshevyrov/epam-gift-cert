package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class GiftCertificateServiceImplTest {


    @InjectMocks
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private GiftCertificateService giftCertificateService;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private GiftCertificateTagDAO giftCertificateTagDAO;
    @Mock
    private GiftCertificate giftCertificate;
    @InjectMocks
    private Tag tag;

    @Test
    void create() {
        giftCertificateService.create(giftCertificate);
//        tagDAO.existByName(tag.getName());
//        tagDAO.create(tag);
//        List<String> mockedList = mock(MyList.class);
//        mockedList.size();
        Mockito.verify(giftCertificateDAO).create(giftCertificate);
        Mockito.verify(tagDAO).existByName(tag.getName());
        Mockito.verify(tagDAO).create(tag);
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByTagName() {
    }
}
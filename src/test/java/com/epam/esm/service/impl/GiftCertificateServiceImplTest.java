package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.*;
import org.mockito.internal.verification.Times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private TagDAO tagDAO;
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private GiftCertificateTagDAO giftCertificateTagDAO;


    private GiftCertificate giftCertificate;

    private List<Tag> tagList;

    private Tag tag;
    @BeforeAll
    public void initTagTest() {
        MockitoAnnotations.openMocks(this);

        giftCertificate = new GiftCertificate();
        tagList = new ArrayList<Tag>() {{
            add(new Tag("Name1"));
            add(new Tag("Name2"));
        }};
       giftCertificate.setTags(tagList);
    }

    @Test
    void createWhenNoNewTag() {
        Mockito.when(tagService.existByName(Mockito.anyString())).thenReturn(false);
        giftCertificateService.create(giftCertificate);
        Mockito.verify(giftCertificateDAO, Mockito.times(1)).create(giftCertificate);
        Mockito.verify(tagDAO, Mockito.times(tagList.size())).create(Mockito.any(Tag.class));
    }
    @Test
    void createWhenNewTag() {
        giftCertificateService.create(giftCertificate);
        Mockito.when(tagDAO.existByName(Mockito.anyString())).thenReturn(true);
//        Mockito.when(tagDAO.findByName(Mockito.any())).thenReturn(new Tag(){{setId(curentId[0]++);setName("Name"+ Arrays.toString(curentId) + Arrays.toString(curentId));}});
        Mockito.verify(giftCertificateDAO, Mockito.times(1)).create(giftCertificate);
        Mockito.verify(tagDAO, Mockito.times(2)).create(Mockito.any(Tag.class));
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
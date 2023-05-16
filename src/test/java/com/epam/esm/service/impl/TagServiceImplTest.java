package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

    private Tag tag;
    private int tagId;
    private int giftCertificateId;
    private String tagName;
    private Map<String,Object> updateMap;

    @BeforeAll
    public void initTagTest() {
        MockitoAnnotations.openMocks(this);

        tag= new Tag("test1");
        tagId=1;
        updateMap = new HashMap<String,Object>(){{put("name","newName");}};
        tagName = "name";
        giftCertificateId = 2;
    }

    @Test
    void create() {
        tagService.create(tag);
        Mockito.verify(tagDAO, Mockito.times(1)).create(tag);
    }

    @Test
    void findById() {
        tagService.findById(tagId);
        Mockito.verify(tagDAO, Mockito.times(1)).findById(tagId);
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

    @Test
    void delete() {
        tagService.delete(tagId);
        Mockito.verify(tagDAO, Mockito.times(1)).deleteById(tagId);
    }

    @Test
    void findAllByGiftCertificateId() {
        tagService.findAllByGiftCertificateId(giftCertificateId);
        Mockito.verify(tagDAO, Mockito.times(1)).findAllByGiftCertificateId(giftCertificateId);
    }

    @Test
    void existByName() {
        tagService.existByName(tagName);
        Mockito.verify(tagDAO, Mockito.times(1)).existByName(tagName);
    }
}
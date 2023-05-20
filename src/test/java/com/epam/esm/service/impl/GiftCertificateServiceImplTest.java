package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.GiftCertificateTagDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.InputVerification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    @InjectMocks
    private TagServiceImpl tagService = new TagServiceImpl(tagDAO);

    @Mock
    private GiftCertificate giftCertificate;
    private GiftCertificate giftCertificateTrueTag;
    private List<Tag> tags;


    @BeforeAll
    public void init() {
        MockitoAnnotations.openMocks(this);
        giftCertificateTrueTag = new GiftCertificate();
        giftCertificateTrueTag.setName("giftCertName1");
        giftCertificateTrueTag.setTags(new ArrayList<Tag>() {{
            add(new Tag("true"));
        }});
        tags = new ArrayList<Tag>() {{
            add(new Tag("11"));
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


    private static Stream<Arguments> generator() {
        GiftCertificate giftCertificateTrueTag = new GiftCertificate();
        giftCertificateTrueTag.setName("giftCertName1");
        Tag tagTrue = new Tag("true");
        Tag tagFalse = new Tag("false");
        giftCertificateTrueTag.setTags(new ArrayList<Tag>() {{
            add(tagTrue);
        }});
        GiftCertificate giftCertificateFalseTag = new GiftCertificate();
        giftCertificateFalseTag.setName("giftCertName2");
        giftCertificateFalseTag.setTags(new ArrayList<Tag>() {{
            add(tagFalse);
        }});

        return Stream.of(
                Arguments.of(giftCertificateTrueTag, true),
                Arguments.of(giftCertificateFalseTag, false));

    }
}
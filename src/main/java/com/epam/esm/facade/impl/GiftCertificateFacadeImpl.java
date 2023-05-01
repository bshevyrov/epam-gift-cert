package com.epam.esm.facade.impl;

import com.epam.esm.veiw.dto.GiftCertificateDTO;
import com.epam.esm.facade.GiftCertificateFacade;
import com.epam.esm.mapper.GiftCertificateListMapper;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagListMapper;
import com.epam.esm.entity.GiftCertificateTag;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.GiftCertificateTagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class GiftCertificateFacadeImpl implements GiftCertificateFacade {

    private final GiftCertificateMapper giftCertificateMapper;
    private final GiftCertificateListMapper giftCertificateListMapper;
    private final GiftCertificateService giftCertificateService;
    private final TagListMapper tagListMapper;
    private final TagService tagService;
    private final GiftCertificateTagServiceImpl gctService;

    @Autowired
    public GiftCertificateFacadeImpl(GiftCertificateMapper giftCertificateMapper, GiftCertificateListMapper giftCertificateListMapper, GiftCertificateService giftCertificateService, TagListMapper tagListMapper, TagService tagService, GiftCertificateTagServiceImpl gctService) {
        this.giftCertificateMapper = giftCertificateMapper;
        this.giftCertificateListMapper = giftCertificateListMapper;
        this.giftCertificateService = giftCertificateService;
        this.tagListMapper = tagListMapper;
        this.tagService = tagService;
        this.gctService = gctService;
    }

    @Override
    public long create(GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateService.create(giftCertificateMapper.toModel(giftCertificateDTO));
    }

    @Override
    public GiftCertificateDTO findById(long id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateMapper.toDTO(giftCertificateService.findById(id));
        giftCertificateDTO.setTags(tagListMapper.toDTOList(tagService.findAllByGiftCertificateId(id)));
        return giftCertificateDTO;
    }

    @Override
    public List<GiftCertificateDTO> findAll() {
        return giftCertificateListMapper.toDTOList(giftCertificateService.findAll());
    }

    @Override
    public void update(Map<String, Object> updates) {
        ArrayList<LinkedHashMap<Object, Object>> tagsList = (ArrayList<LinkedHashMap<Object, Object>>) updates.get("tags");
        if (tagsList.size() != 0) {
            tagsList.forEach(map -> {
                map.forEach((k, v) -> {
                    if (k.equals("name")
                            && !tagService.existByName((String) v)) {
                        gctService.create(new GiftCertificateTag((Long) updates.get("id"), tagService.create(new Tag(v.toString()))));
                    }
                });
            });
        }
    }

    @Override
    public void delete(long id) {
        giftCertificateService.delete(id);
    }
}

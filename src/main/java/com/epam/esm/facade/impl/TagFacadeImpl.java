package com.epam.esm.facade.impl;

import com.epam.esm.facade.TagFacade;
import com.epam.esm.mapper.TagListMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.veiw.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagFacadeImpl implements TagFacade {
    private final TagMapper tagMapper;
    private final TagListMapper tagListMapper;
    private final TagService tagService;

    @Autowired
    public TagFacadeImpl(TagMapper tagMapper, TagListMapper tagListMapper, TagService tagService) {
        this.tagMapper = tagMapper;
        this.tagListMapper = tagListMapper;
        this.tagService = tagService;
    }

    @Override
    public long create(TagDTO tagDTO) {
        return tagService.create(tagMapper.toModel(tagDTO));
    }

    @Override
    public TagDTO findById(long id) {
        return tagMapper.toDTO(tagService.findById(id));
    }

    @Override
    public List<TagDTO> findAll() {
        return tagListMapper.toDTOList(tagService.findAll());
    }

    @Override
    public void update(TagDTO tagDTO) {
        tagService.update(tagMapper.toModel(tagDTO));
    }

    @Override
    public void delete(long id) {
        tagService.delete(id);
    }
}

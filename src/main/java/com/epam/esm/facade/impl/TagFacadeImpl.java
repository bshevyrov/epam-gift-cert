package com.epam.esm.facade.impl;

import com.epam.esm.facade.TagFacade;
import com.epam.esm.mapper.TagListMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.TagService;
import com.epam.esm.veiw.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class used for conversion  tag and tagDTO.
 */
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

    /**
     * Method consume tagDTO and return id of created giftCertificate.
     *
     * @param tagDTO dto object
     * @return id
     */
    @Override
    public long create(TagDTO tagDTO) {
        return tagService.create(tagMapper.toModel(tagDTO));
    }

    /**
     * Method consume id value and return dto object.
     *
     * @param id request parameter
     * @return {@code TagDTO} created object
     */
    @Override
    public TagDTO findById(long id) {
        return tagMapper.toDTO(tagService.findById(id));
    }

    /**
     * Method produce list of dto object.
     *
     * @return List of dtos
     */
    @Override
    public List<TagDTO> findAll() {
        return tagListMapper.toDTOList(tagService.findAll());
    }

    /**
     * Consumes dto update tag.
     *
     * @param tagDTO dto object
     */
    @Override
    public void update(TagDTO tagDTO) {
        tagService.update(tagMapper.toModel(tagDTO));
    }

    /**
     * Consumes id value and deletes tag.
     *
     * @param id requested parameter
     */
    @Override
    public void delete(long id) {
        tagService.delete(id);
    }
}

package com.epam.esm.mapper;

import com.epam.esm.controller.dto.TagDTO;
import com.epam.esm.repository.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<TagDTO> toDTOList(List<Tag> list);
    List<Tag> toModelList(List<TagDTO> list);
}

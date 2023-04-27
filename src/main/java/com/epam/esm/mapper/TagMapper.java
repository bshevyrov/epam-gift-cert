package com.epam.esm.mapper;

import com.epam.esm.controller.dto.TagDTO;
import com.epam.esm.repository.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDTO(Tag tag);
    Tag toModel(TagDTO tagDTO);
}

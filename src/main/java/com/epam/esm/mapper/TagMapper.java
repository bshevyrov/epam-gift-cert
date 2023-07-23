package com.epam.esm.mapper;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.veiw.dto.TagDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDTO(TagEntity tagEntity);

    TagEntity toModel(TagDTO tagDTO);
}

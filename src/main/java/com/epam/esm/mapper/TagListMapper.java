package com.epam.esm.mapper;

import com.epam.esm.entity.TagEntity;
import com.epam.esm.veiw.dto.TagDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface TagListMapper {
    List<TagDTO> toDTOList(List<TagEntity> list);

}

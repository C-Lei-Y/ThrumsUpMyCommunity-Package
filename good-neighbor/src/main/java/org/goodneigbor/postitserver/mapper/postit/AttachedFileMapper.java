package org.goodneigbor.postitserver.mapper.postit;

import org.goodneigbor.postitserver.dto.postit.AttachedFileDto;
import org.goodneigbor.postitserver.entity.postit.AttachedFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AttachedFileMapper {

    public static final AttachedFileMapper INSTANCE = Mappers.getMapper(AttachedFileMapper.class);

    @Mapping(target = "postitNoteId", source = "postitNote.id")
    public abstract AttachedFileDto toDto(AttachedFile entity);

}

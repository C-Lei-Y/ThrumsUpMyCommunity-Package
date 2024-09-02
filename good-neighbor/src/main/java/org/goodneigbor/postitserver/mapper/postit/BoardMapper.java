package org.goodneigbor.postitserver.mapper.postit;

import org.goodneigbor.postitserver.dto.postit.BoardDto;
import org.goodneigbor.postitserver.entity.postit.Board;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper {

    public static final BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    BoardDto toDto(Board entity);

}

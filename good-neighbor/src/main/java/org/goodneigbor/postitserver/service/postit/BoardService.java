package org.goodneigbor.postitserver.service.postit;

import java.util.List;

import org.goodneigbor.postitserver.dto.postit.BoardDto;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;

public interface BoardService {

    List<BoardDto> getBoardList();

    BoardDto saveBoard(Long boardId, BoardDto boardDto) throws NotFoundException, FunctionnalException;

    void deleteBoard(Long boardId);

}

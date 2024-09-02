package org.goodneigbor.postitserver.controller.postit;

import java.util.List;

import org.goodneigbor.postitserver.dto.postit.BoardDto;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.service.postit.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postit")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/boards")
    public List<BoardDto> getBoardList() {
        return boardService.getBoardList();
    }

    @PostMapping("/boards")
    @Secured("ROLE_BOARD_WRITE")
    public BoardDto createBoard(@RequestBody BoardDto boardDto) throws NotFoundException, FunctionnalException {
        return boardService.saveBoard(null, boardDto);
    }

    @PatchMapping("/boards/{id}")
    @Secured("ROLE_BOARD_WRITE")
    public BoardDto updateBoard(@PathVariable("id") Long boardId, @RequestBody BoardDto boardDto)
            throws NotFoundException, FunctionnalException {
        return boardService.saveBoard(boardId, boardDto);
    }

    @DeleteMapping("/boards/{id}")
    @Secured("ROLE_BOARD_WRITE")
    public void deleteBoard(@PathVariable("id") Long boardId) {
        boardService.deleteBoard(boardId);
    }

}

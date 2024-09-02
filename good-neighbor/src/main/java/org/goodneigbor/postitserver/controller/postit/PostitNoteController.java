package org.goodneigbor.postitserver.controller.postit;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.goodneigbor.postitserver.dto.postit.PostitNoteDto;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.service.postit.PostitNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/postit")
public class PostitNoteController {

    @Autowired
    private PostitNoteService postitNoteService;

    @GetMapping("/notes")
    public List<PostitNoteDto> getNoteList(@RequestParam("boardId") Long boardId) {
        return postitNoteService.getNoteList(boardId);
    }

    @GetMapping("/notes/{id}")
    public PostitNoteDto getNote(@PathVariable("id") Long noteId) throws NotFoundException {
        return postitNoteService.getNote(noteId);
    }

    @PostMapping("/notes")
    public PostitNoteDto createNote(@RequestBody PostitNoteDto noteDto)
            throws NotFoundException, InvalidDataException, FunctionnalException {
        return postitNoteService.saveNote(null, noteDto);
    }

    @PatchMapping("/notes/{id}")
    public PostitNoteDto updateNote(@PathVariable("id") Long noteId, @RequestBody PostitNoteDto noteDto)
            throws NotFoundException, InvalidDataException, FunctionnalException {
        return postitNoteService.saveNote(noteId, noteDto);
    }

    @DeleteMapping("/notes/{id}")
    public void deleteNote(@PathVariable("id") Long noteId) {
        postitNoteService.deleteNote(noteId);
    }

    @GetMapping(value = "/notes:export", produces = "text/csv")
    public void exportNotes(HttpServletResponse response) throws IOException {
        postitNoteService.exportNotesToCsv(response.getWriter());
    }

}

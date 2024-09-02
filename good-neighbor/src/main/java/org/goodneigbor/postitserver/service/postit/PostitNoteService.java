package org.goodneigbor.postitserver.service.postit;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Nullable;

import org.goodneigbor.postitserver.dto.postit.PostitNoteDto;
import org.goodneigbor.postitserver.entity.postit.Board;
import org.goodneigbor.postitserver.entity.postit.PostitNote;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;


public interface PostitNoteService {

    List<PostitNoteDto> getNoteList(Long boardId);

    PostitNoteDto getNote(Long noteId) throws NotFoundException;

    PostitNoteDto saveNote(Long noteId, PostitNoteDto noteDto)
            throws NotFoundException, InvalidDataException, FunctionnalException;

    void deleteNote(Long noteId);

    void reorderBoard(Board board, PostitNote noteToChange, @Nullable Integer newNoteOrderNum);

    void exportNotesToCsv(PrintWriter writer) throws IOException;

}

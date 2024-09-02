package org.goodneigbor.postitserver.service.postit.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goodneigbor.postitserver.dao.postit.BoardDao;
import org.goodneigbor.postitserver.dao.postit.PostitNoteDao;
import org.goodneigbor.postitserver.dto.postit.PostitNoteDto;
import org.goodneigbor.postitserver.entity.postit.Board;
import org.goodneigbor.postitserver.entity.postit.PostitNote;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.mapper.postit.PostitNoteMapper;
import org.goodneigbor.postitserver.service.global.GlobalService;
import org.goodneigbor.postitserver.service.postit.PostitNoteService;
import org.goodneigbor.postitserver.util.CheckDataUtil;
import org.goodneigbor.postitserver.util.parameter.ParameterConst;
import org.goodneigbor.postitserver.util.parameter.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.collect.Streams;
import com.opencsv.CSVWriter;

@Service
@Transactional
public class PostitNoteServiceImpl implements PostitNoteService {

    private static final Logger LOGGER = LogManager.getLogger(PostitNoteServiceImpl.class);

    private static final String[] EXPORT_HEADERS = { "board id", "board name", "note id", "note name", "note text",
            "note color", "note order", "attached file" };

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private PostitNoteDao postitNoteDao;

    @Autowired
    private GlobalService globalService;

    @Override
    public List<PostitNoteDto> getNoteList(Long boardId) {
        LOGGER.info("Get NoteList for the board : {}", boardId);
        Iterable<PostitNote> noteIterable = postitNoteDao.findByBoardIdOrderByOrderNum(boardId);
        return Streams.stream(noteIterable).map(PostitNoteMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public PostitNoteDto getNote(Long noteId) throws NotFoundException {
        LOGGER.info("Get Note with the id : {}", noteId);
        Optional<PostitNote> noteOpt = postitNoteDao.findById(noteId);
        return PostitNoteMapper.INSTANCE.toDto(noteOpt.orElseThrow(() -> new NotFoundException("Note")));
    }

    @Override
    public PostitNoteDto saveNote(Long noteId, PostitNoteDto noteDto)
            throws NotFoundException, InvalidDataException, FunctionnalException {
        PostitNote note = null;
        boolean reorderBoard = false;
        Board formerBoard = null;

        if (noteId == null) {
            // Creation
            CheckDataUtil.checkNotNull("boardId", noteDto.getBoardId());
            CheckDataUtil.checkNotNull("name", noteDto.getName());
            Optional<String> maxNoteParameter = globalService.getParameterValue(ParameterConst.NOTE_MAX);
            Long maxNote = ParameterUtil.getLong(maxNoteParameter, 0l);
            if (postitNoteDao.countByBoardId(noteDto.getBoardId()) > maxNote) {
                throw new FunctionnalException("Max Postit Note achieved, creation is blocked");
            }

            note = new PostitNote();
            note.setOrderNum(postitNoteDao.getMaxOrderForByBoardId(noteDto.getBoardId()) + 1);
            LOGGER.info("Create note");

        } else {
            // Update
            Optional<PostitNote> noteOpt = postitNoteDao.findById(noteId);
            note = noteOpt.orElseThrow(() -> new NotFoundException("PostitNote"));
            if (noteDto.getOrderNum() != null) {
                reorderBoard = true;
            }
            LOGGER.info("Update note with the id : {}", noteId);
        }

        if (noteDto.getBoardId() != null) {
            formerBoard = note.getBoard();
            Optional<Board> boardOpt = boardDao.findById(noteDto.getBoardId());
            note.setBoard(boardOpt.orElseThrow(() -> new InvalidDataException("BoardId")));
        }
        if (reorderBoard) {
            reorderBoard(note.getBoard(), note, noteDto.getOrderNum());
            if (formerBoard != null && !note.getBoard().equals(formerBoard)) {
                reorderBoard(formerBoard, note, null);
            }
        }

        PostitNoteMapper.INSTANCE.updateEntity(noteDto, note);

        return PostitNoteMapper.INSTANCE.toDto(postitNoteDao.save(note));
    }

    @Override
    public void reorderBoard(Board board, PostitNote noteToChange, @Nullable Integer newNoteOrderNum) {
        if (newNoteOrderNum != null) {
            if (newNoteOrderNum < 1) {
                newNoteOrderNum = 1;
            } else if (newNoteOrderNum > board.getNoteList().size()) {
                newNoteOrderNum = board.getNoteList().size() + 1;
            }
            noteToChange.setOrderNum(newNoteOrderNum);
        }

        Integer iterateOrderNum = 1;
        for (PostitNote noteOfBoard : board.getNoteList()) {
            if (!noteOfBoard.equals(noteToChange)) {
                if (iterateOrderNum.equals(newNoteOrderNum)) {
                    iterateOrderNum++;
                }
                noteOfBoard.setOrderNum(iterateOrderNum++);
            }
        }
    }

    @Override
    public void deleteNote(Long noteId) {
        Optional<PostitNote> noteOpt = postitNoteDao.findById(noteId);
        if (!noteOpt.isPresent()) {
            LOGGER.warn("Note not found for deletion, id = %s", noteId);
            return;
        }

        // Delete cascade delete also the attachedFile
        postitNoteDao.delete(noteOpt.get());
        LOGGER.info("Delete note with the id : {}", noteId);
    }

    @Override
    public void exportNotesToCsv(PrintWriter writer) throws IOException {
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeNext(EXPORT_HEADERS);

        for (Board board : boardDao.findAll((Sort.by("id").ascending()))) {
            for (PostitNoteDto noteDto : getNoteList(board.getId())) {
                List<String> contentLineList = new ArrayList<>();
                contentLineList.add(board.getId().toString());
                contentLineList.add(board.getName());
                contentLineList.add(noteDto.getId().toString());
                contentLineList.add(noteDto.getName());
                contentLineList.add(noteDto.getText());
                contentLineList.add(noteDto.getColor());
                contentLineList.add(noteDto.getOrderNum().toString());

                if (noteDto.getAttachedFile() == null) {
                    contentLineList.add(null);
                } else {
                    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
                    numberFormat.setRoundingMode(RoundingMode.UP);
                    numberFormat.setMaximumFractionDigits(2);
                    String sizeInKo = numberFormat.format(noteDto.getAttachedFile().getSize() / 1000.0);
                    contentLineList.add(String.format("%s (%s ko)", noteDto.getAttachedFile().getFilename(), sizeInKo));
                }

                csvWriter.writeNext(contentLineList.toArray(new String[contentLineList.size()]));
            }
        }

        csvWriter.close();
        LOGGER.info("Export notes to csv");
    }

}

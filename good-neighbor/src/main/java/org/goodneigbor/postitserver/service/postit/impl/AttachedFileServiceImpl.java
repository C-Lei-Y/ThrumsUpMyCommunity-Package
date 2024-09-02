package org.goodneigbor.postitserver.service.postit.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.goodneigbor.postitserver.dao.postit.AttachedFileDao;
import org.goodneigbor.postitserver.dao.postit.PostitNoteDao;
import org.goodneigbor.postitserver.dto.PageDto;
import org.goodneigbor.postitserver.dto.global.FileToDownload;
import org.goodneigbor.postitserver.dto.postit.AttachedFileDto;
import org.goodneigbor.postitserver.entity.postit.AttachedFile;
import org.goodneigbor.postitserver.entity.postit.AttachedFileData;
import org.goodneigbor.postitserver.entity.postit.PostitNote;
import org.goodneigbor.postitserver.exception.TechnicalException;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.mapper.postit.AttachedFileMapper;
import org.goodneigbor.postitserver.service.postit.AttachedFileService;
import org.goodneigbor.postitserver.util.CheckDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AttachedFileServiceImpl implements AttachedFileService {

    private static final Logger LOGGER = LogManager.getLogger(AttachedFileServiceImpl.class);

    @Autowired
    private AttachedFileDao attachedFileDao;

    @Autowired
    private PostitNoteDao postitNoteDao;

    @Override
    public PageDto<AttachedFileDto> getAttachedFileList(Integer page, Integer size) throws InvalidDataException {
        CheckDataUtil.checkCondition(page >= 0, "page should be equal or greater than 0");
        CheckDataUtil.checkCondition(size >= 1 && size <= 100, "size should be between than 1 and 100");
        LOGGER.info("Get getAttachedFileList for the page {} and the size {}", page, size);

        Pageable pageById = PageRequest.of(page, size, Sort.by("id"));
        Page<AttachedFile> attachedFilesPage = attachedFileDao.findAll(pageById);

        return PageDto.of(attachedFilesPage, AttachedFileMapper.INSTANCE::toDto);
    }

    @Override
    public AttachedFileDto getAttachedFile(Long attachedFileId) throws NotFoundException {
        LOGGER.info("Get AttachedFile with the id : {}", attachedFileId);
        Optional<AttachedFile> attachedFileOpt = attachedFileDao.findById(attachedFileId);
        return AttachedFileMapper.INSTANCE
            .toDto(attachedFileOpt.orElseThrow(() -> new NotFoundException("AttachedFile")));
    }

    @Override
    public FileToDownload getAttachedFileContent(Long attachedFileId) throws NotFoundException, TechnicalException {
        AttachedFile attachedFile = attachedFileDao.findById(attachedFileId)
            .orElseThrow(() -> new NotFoundException("AttachedFile"));

        FileToDownload fileToDownload = new FileToDownload();
        fileToDownload.setFilename(attachedFile.getFilename());
        fileToDownload.setInputStream(new ByteArrayInputStream(attachedFile.getFiledata().getData()));
        fileToDownload.setSize(attachedFile.getSize());
        fileToDownload.setType(attachedFile.getType());
        return fileToDownload;
    }

    @Override
    public AttachedFileDto uploadAttachedFile(Long noteId, MultipartFile file)
            throws NotFoundException, FunctionnalException, TechnicalException, InvalidDataException {
        PostitNote postitNote = postitNoteDao.findById(noteId).orElseThrow(() -> new NotFoundException("PostitNote"));
        if (postitNote.getAttachedFile() != null) {
            throw new FunctionnalException("The note have already a file, delete it first");
        }

        byte[] filedata;
        try {
            CheckDataUtil.checkNotNull("file", file);
            filedata = file.getBytes();
        } catch (IOException exception) {
            throw new TechnicalException("Error getting bytes from file", exception);
        }

        LOGGER.info("Upload AttachedFile {} on the note : {}", file.getOriginalFilename(), noteId);
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFilename(file.getOriginalFilename());
        attachedFile.setFiledata(new AttachedFileData(filedata, attachedFile));
        attachedFile.setSize(file.getSize());
        attachedFile.setType(file.getContentType());
        attachedFile = attachedFileDao.save(attachedFile);

        postitNote.setAttachedFile(attachedFile);

        return AttachedFileMapper.INSTANCE.toDto(attachedFile);
    }

    @Override
    public void deleteAttachedFile(Long attachedFileId) {
        Optional<AttachedFile> attachedFileOpt = attachedFileDao.findById(attachedFileId);
        if (!attachedFileOpt.isPresent()) {
            LOGGER.warn("AttachedFile not found for deletion, id = %s", attachedFileId);
            return;
        }

        // Remove the attached file from the note first
        attachedFileOpt.get().getPostitNote().setAttachedFile(null);
        attachedFileDao.delete(attachedFileOpt.get());

        LOGGER.info("Delete attachedFile with the id : {}", attachedFileId);
    }

}

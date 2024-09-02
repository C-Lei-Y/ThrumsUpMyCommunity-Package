package org.goodneigbor.postitserver.service.postit;

import org.goodneigbor.postitserver.dto.PageDto;
import org.goodneigbor.postitserver.dto.global.FileToDownload;
import org.goodneigbor.postitserver.dto.postit.AttachedFileDto;
import org.goodneigbor.postitserver.exception.TechnicalException;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface AttachedFileService {

    PageDto<AttachedFileDto> getAttachedFileList(Integer page, Integer size) throws InvalidDataException;

    AttachedFileDto getAttachedFile(Long attachedFileId) throws NotFoundException;

    FileToDownload getAttachedFileContent(Long attachedFileId) throws NotFoundException, TechnicalException;

    AttachedFileDto uploadAttachedFile(Long noteId, MultipartFile file)
            throws NotFoundException, FunctionnalException, TechnicalException, InvalidDataException;

    void deleteAttachedFile(Long attachedFileId);

}

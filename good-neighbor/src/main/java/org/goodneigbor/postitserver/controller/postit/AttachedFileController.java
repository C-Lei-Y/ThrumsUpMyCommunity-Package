package org.goodneigbor.postitserver.controller.postit;

import org.goodneigbor.postitserver.dto.PageDto;
import org.goodneigbor.postitserver.dto.global.FileToDownload;
import org.goodneigbor.postitserver.dto.postit.AttachedFileDto;
import org.goodneigbor.postitserver.exception.TechnicalException;
import org.goodneigbor.postitserver.exception.functionnal.FunctionnalException;
import org.goodneigbor.postitserver.exception.functionnal.InvalidDataException;
import org.goodneigbor.postitserver.exception.functionnal.NotFoundException;
import org.goodneigbor.postitserver.service.postit.AttachedFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/postit")
public class AttachedFileController {

    @Autowired
    private AttachedFileService attachedFileService;

    @GetMapping("/attached-files")
    public PageDto<AttachedFileDto> getAttachedFileList(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size) throws InvalidDataException {
        return attachedFileService.getAttachedFileList(page, size);
    }

    @GetMapping("/attached-files/{id}")
    public AttachedFileDto getAttachedFile(@PathVariable("id") Long attachedFileId) throws NotFoundException {
        return attachedFileService.getAttachedFile(attachedFileId);
    }

    @GetMapping("/attached-files/{id}/content")
    public ResponseEntity<InputStreamResource> getAttachedFileContent(@PathVariable("id") Long attachedFileId)
            throws NotFoundException, TechnicalException {
        FileToDownload fileToDownload = attachedFileService.getAttachedFileContent(attachedFileId);

        InputStreamResource resource = new InputStreamResource(fileToDownload.getInputStream());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + fileToDownload.getFilename().replace(" ", "_"));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(fileToDownload.getSize())
            .contentType(MediaType.parseMediaType(fileToDownload.getType()))
            .body(resource);
    }

    @PutMapping("/notes/{noteId}/attached-file")
    public AttachedFileDto uploadAttachedFile(@PathVariable("noteId") Long noteId,
            @RequestParam("file") MultipartFile file)
            throws NotFoundException, FunctionnalException, TechnicalException, InvalidDataException {
        return attachedFileService.uploadAttachedFile(noteId, file);
    }

    @DeleteMapping("/attached-files/{id}")
    public void deleteAttachedFile(@PathVariable("id") Long attachedFileId) {
        attachedFileService.deleteAttachedFile(attachedFileId);
    }

}

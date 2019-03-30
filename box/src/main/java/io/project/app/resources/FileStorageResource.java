package io.project.app.resources;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.FileModel;
import io.project.app.dto.ApiResponseMessage;
import io.project.app.dto.FileDTO;
import io.project.app.services.FileService;
import io.project.app.services.FileStorageService;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/documents")
@Slf4j
public class FileStorageResource {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileService fileService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<?> put(
            @RequestBody(required = true) FileDTO fileDTO
    ) {

        // decode file byte array 
        // final byte[] backToBytes = Base64.decodeBase64(fileDTO.getFileContent());
        String filepath = fileStorageService.storeFile(fileDTO.getFileName(), fileDTO.getFileContent(), fileDTO.getUserId());

        if (filepath != null) {
            FileModel fileModel = new FileModel();
            fileModel.setFileName(fileDTO.getFileName());
            fileModel.setFilePath(filepath);
            fileModel.setContentType(fileDTO.getContentType());
            fileModel.setFileSize(fileDTO.getFileSize());
            fileModel.setUserId(fileDTO.getUserId());
            fileModel.setUploadDate(new Date(System.currentTimeMillis()));
            log.info("File size is" + fileModel.getFileSize());
            log.info("Content type is" + fileModel.getContentType());
            Optional<FileModel> savedFileMetadata = fileService.saveFileMetadata(fileModel);
            if (savedFileMetadata.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(savedFileMetadata.get().getId());
            }
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponseMessage("Could not save file"));
    }

    @GetMapping
    @ResponseBody
    @CrossOrigin
    @Timed
    public ResponseEntity<?> fetch(
            @RequestParam(name = "id", required = true) String id
    ) {

        FileDTO userFile = fileService.findFile(id);

        return ResponseEntity.status(HttpStatus.OK).body(userFile);
    }

    @DeleteMapping
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<?> delete(
            @RequestParam(name = "id", required = true) String id
    ) {

        fileService.deleteById(id);
        log.info("File has been deleted");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponseMessage("File has been deleted"));
    }

}

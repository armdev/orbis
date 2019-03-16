/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.resources;

import io.project.app.services.FileService;
import io.micrometer.core.annotation.Timed;
import io.project.app.domain.FileModel;
import io.project.app.dto.FileDTO;

import io.project.app.services.FileStorageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author armen
 */
@RestController
@RequestMapping("/api/v2/documents")
@Slf4j
public class DocumentController {

    @Autowired
    private FileStorageService fileStorageUtil;

    @Autowired
    private FileService fileModelDAO;


    @PostMapping(path = "/document/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<?> upload(
          
            @RequestParam(name = "file", required = true) MultipartFile file,
            @RequestParam(name = "organizationId", required = true) Long organizationId
           
    ) throws IOException {

      
        if (file.isEmpty()) {
            log.info("Empty file");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        log.info("************** Document save started. File is not empty ***************");

        byte[] bytes = file.getBytes();

//        Tika tika = new Tika();
//        String mimeType = tika.detect(bytes);
//        log.info("mimetype is " + mimeType);
////        if (MimeType.fromString(mimeType) == null) {
////            log.info("Could not upload this type of file " + mimeType);
////            //throw new BadRequestException("Could not Upload this type of File");
////        }

        String filepath = fileStorageUtil.storeFile("Axele", file.getOriginalFilename(), bytes, organizationId, organizationId);

        if (!Optional.ofNullable(filepath).isPresent()) {
            //throw new ServerErrorException("Could not store a file!");
        }

        log.info("filepath: " + filepath);

        FileModel fileModel = new FileModel();
        fileModel.setFileName(file.getOriginalFilename());
        fileModel.setFilePath(filepath);
        //fileModel.setContentType(mimeType);
        fileModel.setFileSize(file.getSize());
        fileModel.setOrganizationId(organizationId);
        fileModel.setOwnerId(organizationId);
        fileModel.setUploadDate(new Date(System.currentTimeMillis()));
        log.info("Document size is" + fileModel.getFileSize());
        log.info("Content type is" + fileModel.getContentType());

        Optional<FileModel> savedFileModel = fileModelDAO.save(fileModel);

       
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFileModel.get());
    }

    @DeleteMapping("/document/delete")
    @CrossOrigin
    public ResponseEntity<?> deleteDocument(
           
            @RequestParam(name = "id", required = true) String id
    ) {
      

        fileModelDAO.deleteById(id);
        log.info("The Document deleted successfully!");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/document/get/list")
    @ResponseBody
    @CrossOrigin
    @Timed
    public ResponseEntity<?> getDocumentList(
          
            @RequestParam(name = "organizationId", required = true) Long organizationId,
            @RequestParam(name = "id", required = true) List<String> ids
    ) {

       
         
        log.info("Find document by id-" + ids);
        Optional<List<FileModel>> fileModels = fileModelDAO.findAllByOrganizationIdAndIdIn(organizationId, ids);

        List<FileDTO> resultList = new ArrayList<>();
        log.info("Found document by given id-" + ids);
//
//        fileModels.get().stream().map((file) -> serviceMapper.toFileDTO(file)).forEachOrdered((fileDTO) -> {
//            resultList.add(fileDTO);
//        });
        log.info("Return documents DTO");
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    @GetMapping("/document/get")
    @ResponseBody
    @CrossOrigin
    @Timed
    public ResponseEntity<?> getDocument(
         
            @RequestParam(name = "organizationId", required = true) Long organizationId,
            @RequestParam(name = "id", required = true) String id
    ) {

      

        log.info("Find document by id-" + id);
        Optional<FileModel> fileModel = fileModelDAO.findByOrganizationIdAndId(organizationId, id);

//        if (!fileModel.isPresent()) {
//            throw new EntityNotFoundException("Could not find document with given organizationId and id " + organizationId + " " + id);
//        }

        log.info("Found document by given id-" + id);


        return ResponseEntity.status(HttpStatus.OK).body(fileModel.get());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.services;

import io.project.app.domain.FileModel;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.project.app.repositories.FileModelRepository;

/**
 *
 * @author armen
 */
@Service
@Component
@Slf4j
public class FileService {

    @Autowired
    private FileModelRepository fileModelRepository;

    @Autowired
    private FileStorageService fileStorageUtil;
    
      public Optional<FileModel> findByIdAndUserId(String id, String userId) {

        
        return fileModelRepository.findByIdAndUserId(id, userId);

    }

    public Optional<FileModel> findById(String id) {

        log.info("Start findById id " + id);

        Optional<FileModel> fileModel = fileModelRepository.findById(id);

        if (fileModel.isPresent()) {
            log.info("GOOD CASE: fileModel is not empty, found result with given id-" + id);
            return fileModel;
        }
        log.info("BAD CASE: did not found any result with id , returning empty" + id);
        return Optional.empty();

    }

    public void deleteById(String id) {
        log.info("Start deleteById id " + id);
        Optional<FileModel> fileMetaData = this.findById(id);

        if (fileMetaData.isPresent()) {
            log.info("Document metadata is present");
            boolean removeFile = fileStorageUtil.removeFile(fileMetaData.get().getFilePath());

            if (removeFile) {
                fileModelRepository.deleteById(id);
                log.info("Deleted fileModel by id " + id);
            }
            log.info("File metadata did not deleted");
        }
        log.info("Could not find file metadata wit id " + id);
    }

    @Transactional(transactionManager = "transactionManager", readOnly = false)
    public Optional<FileModel> saveFileMetadata(
            FileModel fileModel
    ) {
        log.info("FileModel save is started:");

        FileModel savedFileModel = fileModelRepository.save(fileModel);
        if (Optional.ofNullable(savedFileModel.getId()).isPresent()) {
            log.info("Saved FileModel!");
            return Optional.ofNullable(savedFileModel);
        }
        log.info("Could not save FileModel");
        return Optional.empty();
    }

}

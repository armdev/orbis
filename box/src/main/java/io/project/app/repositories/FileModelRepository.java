/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.repositories;


import io.project.app.domain.FileModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author armen
 */
@Repository
@Component
public interface FileModelRepository extends MongoRepository<FileModel, String> {

    List<FileModel> findAllByOrganizationId(Long organizationId);
    
    Optional<List<FileModel>> findAllByOrganizationIdAndIdIn(Long organizationId, List<String> ids);
    
    Optional<FileModel> findByOrganizationIdAndId(Long organizationId, String id);

}

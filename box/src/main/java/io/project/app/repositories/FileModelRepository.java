package io.project.app.repositories;

import io.project.app.domain.FileModel;
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

    Optional<FileModel> findByIdAndUserId(String id, String userId);

}

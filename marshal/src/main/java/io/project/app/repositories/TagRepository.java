package io.project.app.repositories;

import io.project.app.domain.Tag;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author armena
 */
@Component
@Repository
public interface TagRepository extends MongoRepository<Tag, String> {
    
    Optional<Tag> findByTag(String tag);
    
}

package io.project.app.repositories;

import io.project.app.domain.Question;
import java.util.List;
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
public interface QuestionRepository extends MongoRepository<Question, String> {
    
    Optional<List<Question>> findAllByOrderByPublishDateDesc();
    Optional<List<Question>> findAllByUserIdOrderByPublishDateDesc(String userId);
    
}

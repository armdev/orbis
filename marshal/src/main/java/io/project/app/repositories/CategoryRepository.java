package io.project.app.repositories;

import io.project.app.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author armena
 */
@Component
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

}

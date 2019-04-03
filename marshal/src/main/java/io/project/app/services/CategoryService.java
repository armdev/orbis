package io.project.app.services;

import io.project.app.domain.Category;
import io.project.app.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 *
 * @author root
 */
@Service
@Component
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

   
    public Category addQuestion(Category category) {
        category.setPublishDate(new Date(System.currentTimeMillis()));
        return categoryRepository.save(category);
    }

   
}

package io.project.app.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import io.project.app.repositories.QuestionRepository;

/**
 *
 * @author root
 */
@Service
@Component
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionRepository userRepository;  

    @Autowired
    private MongoTemplate mongoTemplate;

    
}

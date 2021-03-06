package io.project.app.services;

import io.project.app.domain.Answer;
import io.project.app.domain.Question;
import io.project.app.domain.Tag;
import io.project.app.repositories.AnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import io.project.app.repositories.QuestionRepository;
import io.project.app.repositories.TagRepository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author root
 */
@Service
@Component
@Slf4j
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TagRepository tagRepository;

    public Optional<Question> findById(String id) {
        return questionRepository.findById(id);
    }
    
    public Optional<List<Question>> findUserQuestions(String id) {
        log.info("Get all questions by user id ");
        return questionRepository.findAllByUserIdOrderByPublishDateDesc(id);
    }

    public Optional<List<Question>> findAllQuestions() {
        log.info("get 100 questions");
        return questionRepository.findTop100ByOrderByPublishDateDesc();
    }

    public Question addQuestion(Question question) {
        question.setId(null);
        question.setPublishDate(new Date(System.currentTimeMillis()));

        question.getTags().forEach((str) -> {
            Optional<Tag> findByTag = tagRepository.findByTag(str);
            Tag tag = null;
            if (!findByTag.isPresent()) {
                tag = new Tag();
                tag.setTag(str);
                tag.setPublishDate(new Date(System.currentTimeMillis()));
                tagRepository.save(tag);
            }
        });
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question questions) {
        questions.setUpdateDate(new Date(System.currentTimeMillis()));
        return questionRepository.save(questions);
    }

    public Optional<Answer> addAnswer(Answer answer) {
        log.info("Started add anwser");
        Optional<Question> question = questionRepository.findById(answer.getQuestionId());
        
        if (question.isPresent()) {
            log.info("question is present");
            answer.setPublishDate(new Date(System.currentTimeMillis()));
            
            Answer savedAnswer = answerRepository.save(answer);
            log.info("saved Answer  " + savedAnswer.toString());
            question.get().getAnswers().add(0,savedAnswer);
            
            questionRepository.save(question.get());
            return Optional.ofNullable(savedAnswer);
        }
        return Optional.ofNullable(answer);
    }

    public Optional<Answer> updateAnswer(Answer answer) {
        Optional<Question> question = questionRepository.findById(answer.getQuestionId());
        if (question.isPresent()) {
            answer.setUpdateDate(new Date(System.currentTimeMillis()));
            Answer savedAnswer = answerRepository.save(answer);
            question.get().getAnswers().add(savedAnswer);
            return Optional.ofNullable(savedAnswer);
        }
        return Optional.ofNullable(answer);
    }

}

package io.project.app.resources;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.Answer;
import io.project.app.domain.Question;
import io.project.app.services.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/questions")
@Slf4j
@Api(value = "Question API")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(path = "/question", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    @ApiOperation(value = "Add question", notes = "Add question")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful")
        , @ApiResponse(code = 400, message = "Exception")})
    public ResponseEntity<?> post(@RequestBody Question question) {

        if (question.getCategoryId() == null || question.getUserId() == null || question.getUsername() == null || question.getTitle() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of mandatory fields is empty");
        }

        Question addQuestion = questionService.addQuestion(question);

        if (addQuestion.getId() == null) {
            log.error("Cant save question");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during save");
        }
        return ResponseEntity.status(HttpStatus.OK).body(addQuestion);
    }

    @GetMapping(path = "/find/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> get() {
        log.info("Find all questions");
        return ResponseEntity.status(HttpStatus.OK).body(questionService.findAllQuestions().get());
    }

    @GetMapping(path = "/find/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> find(@RequestParam(name = "id", required = true) String id) {
        log.info("Find  question by id " + id);
        return ResponseEntity.status(HttpStatus.OK).body(questionService.findById(id).get());
    }

    @PostMapping(path = "/answer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    @ApiOperation(value = "Add answer", notes = "Add answer")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful")
        , @ApiResponse(code = 400, message = "Exception")})
    public ResponseEntity<?> put(@RequestBody Answer answer) {

        if (answer.getQuestionId() == null || answer.getUserId() == null || answer.getUsername() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One of mandatory fields is empty");
        }

        Optional<Answer> addAnswer = questionService.addAnswer(answer);

        if (!addAnswer.isPresent()) {
            log.error("Cant save answer");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error during save");
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(addAnswer.get());
    }

}

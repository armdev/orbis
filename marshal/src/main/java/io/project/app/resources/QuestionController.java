package io.project.app.resources;

import io.project.app.services.QuestionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
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

   


}

package io.project.app.resources;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.User;
import io.project.app.services.UserService;
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
@RequestMapping("/api/v2/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

   

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> simpleSeach(@RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "age", required = false) int age, @RequestParam(name = "preferences", required = false) String preferences) {
        log.info("Search started ");
        log.info("age " + age);
        log.info("preferences " + preferences);
        log.info("gender " + gender);
        //return ResponseEntity.status(HttpStatus.OK).body(userService.doSearch(gender, age, preferences));
         return ResponseEntity.status(HttpStatus.OK).body("done");
    }

}

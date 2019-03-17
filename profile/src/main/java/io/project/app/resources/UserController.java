package io.project.app.resources;

import io.micrometer.core.annotation.Timed;
import io.project.app.services.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.project.app.domain.User;
import java.util.Optional;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/profiles")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> put(@RequestBody User user) {
        if (user.getId() != null) {
            Optional<User> findUser = userService.findUser(user.getId());
            if (findUser.isPresent()) {
                User updateUser = userService.updateUser(user);
                return ResponseEntity.status(HttpStatus.OK).body(updateUser);
            }

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find user for update");
    }

}

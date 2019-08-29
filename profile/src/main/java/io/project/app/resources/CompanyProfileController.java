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
import io.project.app.api.requests.PasswordUpdateRequest;
import io.project.app.util.PasswordHash;
import java.util.Optional;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/organization")
@Slf4j
public class CompanyProfileController {

    @Autowired
    private UserService userService;

    @PutMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> put(@RequestBody User user) {
        log.info("Started user update");
        if (user.getId() != null) {
            Optional<User> findUser = userService.findUser(user.getId());
            if (findUser.isPresent()) {
                User updateUser = userService.updateUser(user);
                return ResponseEntity.status(HttpStatus.OK).body(updateUser);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find user for update");
    }

    @PutMapping(path = "/user/password", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> update(@RequestBody PasswordUpdateRequest passwordUpdate) {
        log.info("Started password update");
        if (passwordUpdate.getId() != null) {
            Optional<User> findUser = userService.findUser(passwordUpdate.getId());
            if (findUser.isPresent()) {
                findUser.get().setPassword(PasswordHash.hashPassword(passwordUpdate.getPassword()));
                User updateUser = userService.updateUser(findUser.get());
                return ResponseEntity.status(HttpStatus.OK).body(updateUser);
            }

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not find user for update");
    }

}

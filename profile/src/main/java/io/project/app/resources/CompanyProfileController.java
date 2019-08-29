package io.project.app.resources;

import io.micrometer.core.annotation.Timed;
import io.project.app.domain.CareerAccount;
import io.project.app.domain.ShipperAccount;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.project.app.domain.User;
import io.project.app.services.CareerProfileService;
import io.project.app.services.ShipperProfileService;
import io.project.app.services.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author armena
 */
@RestController
@RequestMapping("/api/v2/organization")
@Slf4j
public class CompanyProfileController {

    @Autowired
    private CareerProfileService careerProfileService;
    
    @Autowired
    private ShipperProfileService shipperProfileService;

    @Autowired
    private UserService userService;

    @PutMapping(path = "/shipper", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> put(@RequestBody ShipperAccount shipperAccount) {
        log.info("Shipper profile update");
        Optional<User> findUser = userService.findUser(shipperAccount.getId());
        if (findUser.isPresent()) {
            ShipperAccount updateAccount = shipperProfileService.saveOrUpdate(shipperAccount);
            if (updateAccount.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK).body(updateAccount);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not update account");
    }

    @PutMapping(path = "/career", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> update(@RequestBody CareerAccount careerAccount) {
        log.info("career profile update");
        Optional<User> findUser = userService.findUser(careerAccount.getId());
        if (findUser.isPresent()) {
            CareerAccount updateAccount = careerProfileService.saveOrUpdate(careerAccount);
            if (updateAccount.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK).body(updateAccount);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not update account");
    }

    @GetMapping(path = "/shipper/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> findShipperAccount(@RequestParam String id) {
        log.info("Shipper profile load");
        Optional<User> findUser = userService.findUser(id);
        if (findUser.isPresent()) {
            ShipperAccount updateAccount = shipperProfileService.findProfile(id).get();
            if (updateAccount.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK).body(updateAccount);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not update account");
    }

    @GetMapping(path = "/career/id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @CrossOrigin
    @Timed
    public ResponseEntity<?> findCareerAccount(@RequestParam String id) {
        log.info("Career profile load");
        Optional<User> findUser = userService.findUser(id);
        if (findUser.isPresent()) {
            CareerAccount updateAccount = careerProfileService.findProfile(id).get();
            if (updateAccount.getId() != null) {
                return ResponseEntity.status(HttpStatus.OK).body(updateAccount);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not update account");
    }

}

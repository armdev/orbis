package io.project.app.services;

import io.project.app.domain.ShipperAccount;
import io.project.app.domain.User;
import io.project.app.repositories.ShipperRepository;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armen
 */
@Service
@Component
@Slf4j
public class ShipperProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private ShipperRepository shipperRepository;

    public ShipperAccount saveOrUpdate(ShipperAccount shipperAccount) {
        Optional<User> findUser = userService.findUser(shipperAccount.getUserId());
        if (findUser.isPresent()) {
            log.info("Account belong to the " + findUser.get().getEmail());
            if (findUser.get().getAccountType().equalsIgnoreCase("Shipper")) {
                log.info("We can update career account");
                shipperAccount.setUpdateAt(new Date(System.currentTimeMillis()));
                return shipperRepository.save(shipperAccount);
            }
        }
        log.error("Wrong credentials for profile update");
        return shipperAccount;

    }

    public Optional<ShipperAccount> findProfile(String id) {
        return shipperRepository.findByUserId(id);

    }

}

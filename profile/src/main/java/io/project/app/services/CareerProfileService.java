package io.project.app.services;

import io.project.app.domain.CareerAccount;
import io.project.app.domain.User;
import io.project.app.repositories.CareerRepository;
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
public class CareerProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private CareerRepository careerRepository;

    public CareerAccount saveOrUpdate(CareerAccount careerAccount) {
        Optional<User> findUser = userService.findUser(careerAccount.getUserId());
        if (findUser.isPresent()) {
            log.info("Account belong to the " + findUser.get().getEmail());
            if (findUser.get().getAccountType().equalsIgnoreCase("Career")) {
                log.info("We can update career account");
                careerAccount.setUpdateAt(new Date(System.currentTimeMillis()));
                return careerRepository.save(careerAccount);
            }
        }
        log.error("Wrong credentials for profile update");
        return careerAccount;
    }

    public Optional<CareerAccount> findProfile(String id) {
        return careerRepository.findByUserId(id);

    }

}

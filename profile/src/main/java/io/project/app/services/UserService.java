package io.project.app.services;

import io.project.app.domain.User;
import io.project.app.repositories.UserRepository;
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
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public Optional<User> findUser(String id) {
        return userRepository.findById(id);
    }

}

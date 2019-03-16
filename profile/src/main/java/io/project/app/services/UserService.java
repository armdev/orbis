package io.project.app.services;

import io.project.app.domain.User;
import io.project.app.dto.UserData;
import io.project.app.repositories.UserRepository;
import io.project.app.util.CommonConstants;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author root
 */
@Service
@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;

    public User save(User user) {
        
       // gridFsTemplate.store(content, filename);
        user.setStatus(CommonConstants.ACTIVATED);
        User savedUser = userRepository.save(user);
        UserData userData = new UserData(savedUser.getId(), savedUser.getFirstname(), savedUser.getLastname());

        return savedUser;
    }

  

}

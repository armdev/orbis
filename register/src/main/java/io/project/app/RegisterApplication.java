package io.project.app;

import io.project.app.domain.User;
import io.project.app.repositories.UserRepository;
import io.project.app.services.UserService;
import io.project.app.util.PasswordHash;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("io.project.app.repositories")
@ComponentScan("io.project.app")
@EntityScan("io.project.app.domain")
@EnableEurekaClient
@EnableDiscoveryClient
public class RegisterApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(RegisterApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        userService.save(new User("Adam", "Trust", "a@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "MALE", 25, new Date(), 1));
        userService.save(new User("Ben", "Red", "b@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "MALE", 35, new Date(), 1));
        userService.save(new User("Den", "White", "c@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "MALE", 33, new Date(), 1));
        userService.save(new User("Carlos", "Alarm", "d@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "MALE", 22, new Date(), 1));
        userService.save(new User("Lina", "Ross", "e@gmail.com", PasswordHash.hashPassword("aaaaaa"), "098545242", "FEMALE", 26, new Date(), 1));

    }
}

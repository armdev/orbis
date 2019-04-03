package io.project.app.marshal;

import io.project.app.domain.Category;
import io.project.app.repositories.CategoryRepository;
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
public class MarshalApplication implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(MarshalApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        categoryRepository.deleteAll();
        categoryRepository.save(new Category("Human Behavior", new Date(), new Date(), 1));
        categoryRepository.save(new Category("Business", new Date(), new Date(), 1));
        categoryRepository.save(new Category("Sport", new Date(), new Date(), 1));
        categoryRepository.save(new Category("Philosophy", new Date(), new Date(), 1));
        categoryRepository.save(new Category("DevOps", new Date(), new Date(), 1));
        categoryRepository.save(new Category("Programming", new Date(), new Date(), 1));
        categoryRepository.save(new Category("Politics", new Date(), new Date(), 1));
        categoryRepository.save(new Category("Entertainment", new Date(), new Date(), 1));
        categoryRepository.save(new Category("Startups", new Date(), new Date(), 1));

    }

}

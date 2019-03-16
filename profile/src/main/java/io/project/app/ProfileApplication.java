package io.project.app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("io.project.app")
@EntityScan("io.project.app.domain")
@EnableEurekaClient
@EnableDiscoveryClient
public class ProfileApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(ProfileApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }
//    
//    @Bean
//    public GridFsTemplate gridFsTemplate() throws Exception {
//        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
//    }

}

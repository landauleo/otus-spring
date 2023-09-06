package src.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import src.otus.spring.service.DeveloperService;

@SpringBootApplication
@IntegrationComponentScan
public class Hw15 {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Hw15.class, args);
        DeveloperService developerService = ctx.getBean(DeveloperService.class);
        developerService.start();
    }

}

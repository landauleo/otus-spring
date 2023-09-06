package src.otus.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import src.otus.spring.service.DeveloperServiceImpl;

//https://docs.spring.io/spring-integration/docs/4.3.12.RELEASE/reference/html/messaging-channels-section.html
@Configuration
public class IntegrationConfig {

    @Bean
    public QueueChannel juniorsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public QueueChannel middlesChannel() {
        return MessageChannels.queue(5).get();
    }

    @Bean
    public QueueChannel seniorsChannel() {
        return MessageChannels.queue(2).get();
    }

    @Bean
    public IntegrationFlow juniorsFlow(DeveloperServiceImpl developerService) {
        return IntegrationFlows.from(juniorsChannel())
                .split()
                .handle(developerService, "becomeMiddleDeveloper")
                .aggregate()
                .channel(middlesChannel())
                .get();
    }

    @Bean
    public IntegrationFlow middlesFlow(DeveloperServiceImpl developerService) {
        return IntegrationFlows.from(middlesChannel())
                .split()
                .handle(developerService, "becomeSeniorDeveloper")
                .aggregate()
                .channel(seniorsChannel())
                .get();
    }

}

package src.otus.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import src.otus.spring.service.DeveloperServiceImpl;

@Configuration
public class IntegrationConfig {

    @Bean
    public QueueChannel juniorsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel middlesChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel seniorsChannel() {
        return MessageChannels.publishSubscribe().get();
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

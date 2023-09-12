package src.otus.spring.integration;


import java.util.Collection;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import src.otus.spring.domain.Junior;

@MessagingGateway
public interface JuniorsGateway {

    @Gateway(requestChannel = "juniorsChannel")
    void process(Collection<Junior> juniors);

}

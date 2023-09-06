package src.otus.spring.integration;


import java.util.Collection;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import src.otus.spring.domain.Middle;
import src.otus.spring.domain.Senior;

@MessagingGateway
public interface MiddlesGateway {

    @Gateway(requestChannel = "middlesChannel", replyChannel = "seniorsChannel")
    Collection<Senior> process(Collection<Middle> middles);

}

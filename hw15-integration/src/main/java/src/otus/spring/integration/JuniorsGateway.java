package src.otus.spring.integration;


import java.util.Collection;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import src.otus.spring.domain.Junior;
import src.otus.spring.domain.Middle;

@MessagingGateway
public interface JuniorsGateway {

    @Gateway(requestChannel = "juniorsChannel", replyChannel = "middlesChannel")
    Collection<Middle> process(Collection<Junior> juniors);

}

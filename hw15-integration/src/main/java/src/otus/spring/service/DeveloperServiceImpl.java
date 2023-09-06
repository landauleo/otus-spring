package src.otus.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import src.otus.spring.domain.Junior;
import src.otus.spring.domain.Middle;
import src.otus.spring.domain.Senior;
import src.otus.spring.integration.JuniorsGateway;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private final JuniorsGateway juniorsGateway;

    private static final Logger log = LoggerFactory.getLogger(DeveloperServiceImpl.class);

    public DeveloperServiceImpl(JuniorsGateway juniorsGateway) {
        this.juniorsGateway = juniorsGateway;
    }

    @Override
    public Middle becomeMiddleDeveloper(Junior junior) {
        log.info("(°◡°♡) YAS, I'm becoming middle developer");
        return new Middle((short) 50);
    }

    @Override
    public Senior becomeSeniorDeveloper(Middle middle) {
        log.info("(눈_눈) eh, wait, am I a senior developer? alrighty...");
        return new Senior((short) 100);
    }

    @Override
    public void start() {
        juniorsGateway.process(List.of(new Junior((short) 25)));
    }

}

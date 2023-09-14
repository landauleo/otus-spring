package src.otus.spring.service;

import src.otus.spring.domain.Junior;
import src.otus.spring.domain.Middle;
import src.otus.spring.domain.Senior;

public interface DeveloperService {

    Middle becomeMiddleDeveloper(Junior junior);

    Senior becomeSeniorDeveloper(Middle middle);

    void start();

}

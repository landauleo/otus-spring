package ru.otus.spring.shell;

import java.util.List;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.validator.Validator;
import ru.otus.spring.service.validator.ValidatorImpl;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellComponent
public class ValidatorCommands {

    private final Validator validator;

    private List<String> expected;
    private List<String> actual;

    public ValidatorCommands(ValidatorImpl validator) {
        this.validator = validator;
    }

    @ShellMethod(value = "Run command", key = {"start", "run"})
    @ShellMethodAvailability(value = "canValidate")
    public void validate() {
        validator.validate(expected, actual);
    }

    private Availability canValidate() {
        return expected != null && !expected.isEmpty() && actual != null && !actual.isEmpty() ? available() : unavailable("Сначала введите ответы!");
    }

}

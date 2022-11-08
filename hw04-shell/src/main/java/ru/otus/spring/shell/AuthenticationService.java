package ru.otus.spring.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.MessageSourceService;
import ru.otus.spring.service.QuestionnaireService;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellCommandGroup("Questionnaire auth commands")
@ShellComponent
public class AuthenticationService {

    private final MessageSourceService messageSource;
    private final IOService ioService;
    private final QuestionnaireService questionnaireService;
    private String userName;

    public AuthenticationService(MessageSourceService messageSource, IOService ioService, QuestionnaireService questionnaireService) {
        this.messageSource = messageSource;
        this.ioService = ioService;
        this.questionnaireService = questionnaireService;
    }

    @ShellMethod(value = "Authenticate user", key = {"1", "start", "auth", "authenticate"})
    @ShellMethodAvailability(value = "shouldAuthenticate")
    public void authenticate() {
        messageSource.printMessage("authentication.user", null);
        userName = ioService.readString().trim().toLowerCase(Locale.ROOT);
        messageSource.printMessage("greeting.user", new String[]{String.valueOf(userName)});
        questionnaireService.run();
    }

    private Availability shouldAuthenticate() {
        return userName == null ? available() : unavailable("User has already introduced");
    }
}

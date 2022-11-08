package ru.otus.spring.shell;

import java.util.Locale;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.MessageSourceService;
import ru.otus.spring.service.QuestionnaireService;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellCommandGroup("Questionnaire auth commands")
@ShellComponent
public class QuestionnaireCommandImpl implements QuestionnaireCommand {

    private final MessageSourceService messageSource;
    private final IOService ioService;
    private final QuestionnaireService questionnaireService;
    private String userName;

    public QuestionnaireCommandImpl(MessageSourceService messageSource, IOService ioService, QuestionnaireService questionnaireService) {
        this.messageSource = messageSource;
        this.ioService = ioService;
        this.questionnaireService = questionnaireService;
    }

    @Override
    @ShellMethod(value = "Start quiz for new user", key = {"1", "start", "begin", "палочки_да_ёлочки"})
    @ShellMethodAvailability(value = "canStart")
    public void start() {
        messageSource.printMessage("introduction.user", null);
        userName = ioService.readString().trim().toLowerCase(Locale.ROOT);
        messageSource.printMessage("greeting.user", new String[]{userName});
        questionnaireService.run();
    }

    private Availability canStart() {
        return userName == null ? available() : unavailable("Only new users can be introduced");
    }

}

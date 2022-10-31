package ru.otus.spring.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.QuestionnaireService;

@ShellCommandGroup("Questionnaire Commands")
@ShellComponent
public class QuestionnaireServiceCommands {

    private final QuestionnaireService questionnaireService;

    public QuestionnaireServiceCommands(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @ShellMethod(value = "Run command", key = {"start", "run"})
    public void run() {
        questionnaireService.run();
    }

}

package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppProps;
import ru.otus.spring.domain.Option;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final Reader reader;
    private final Validator validator;
    private final MessageSource messageSource;
    private final AppProps appProps;
    private Locale locale;

    //тут должен сработать автовайринг по дефолту, так как всего лишь 1 конструктор
    public QuestionnaireServiceImpl(Reader reader, Validator validator, MessageSource messageSource, AppProps appProps) {
        this.reader = reader;
        this.validator = validator;
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    //если PostConstruct плохо, то как лучше?
    @PostConstruct
    public void defineLocale() {
        locale = appProps.getLocale();
    }

    @Override
    public void run() {
        System.out.println(messageSource.getMessage("greeting.user", new String[]{"(◕‿◕)"}, locale));
        System.out.println(messageSource.getMessage("greeting.warning", null, locale));

        Scanner scanner = new Scanner(System.in);

        List<Question> questions = reader.read();
        List<Option> userAnswers = new ArrayList<>();
        List<Option> rightAnswers = getRightAnswers(questions);

        for (Question question : questions) {
            System.out.println(question.getText().text());
            for (Option option : question.getOptions()) {
                System.out.println(option.text());
            }
            userAnswers.add(new Option(scanner.nextLine().trim().toLowerCase(Locale.ROOT)));
        }

        validator.validate(rightAnswers, userAnswers);
    }

    private List<Option> getRightAnswers(List<Question> questions) {
        return questions.stream().map(Question::getAnswer).collect(Collectors.toList());
    }
}

package ru.otus.spring.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Option;
import ru.otus.spring.domain.Question;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final Reader reader;
    private final Validator validator;
    private final MessageSourceService messageSource;

    //тут должен сработать автовайринг по дефолту, так как всего лишь 1 конструктор
    public QuestionnaireServiceImpl(Reader reader, Validator validator, MessageSourceService messageSource) {
        this.reader = reader;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    public void run() {
        messageSource.getAndPrintMessage("greeting.user", new String[]{"(◕‿◕)"});
        messageSource.getAndPrintMessage("greeting.warning", null);

        Scanner scanner = new Scanner(System.in);

        List<Question> questions = reader.read();
        List<String> userAnswers = new ArrayList<>();
        List<String> rightAnswers = getRightAnswers(questions);

        for (Question question : questions) {
            System.out.println(question.getText());
            for (Option option : question.getOptions()) {
                System.out.println(option.getText());
            }
            userAnswers.add(scanner.nextLine().trim().toLowerCase(Locale.ROOT));
        }

        validator.validate(rightAnswers, userAnswers);
    }

    private List<String> getRightAnswers(List<Question> questions) {
        return questions.stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(Option::getText).collect(Collectors.toList());
    }
}

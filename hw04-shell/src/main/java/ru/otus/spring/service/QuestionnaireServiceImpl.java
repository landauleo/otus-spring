package ru.otus.spring.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Option;
import ru.otus.spring.domain.Question;
import ru.otus.spring.repository.QuestionRepository;
import ru.otus.spring.service.validator.Validator;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final QuestionRepository questionRepository;
    private final Validator validator;
    private final MessageSourceService messageSource;
    private final IOService ioService;

    //тут должен сработать автовайринг по дефолту, так как всего лишь 1 конструктор
    public QuestionnaireServiceImpl(QuestionRepository questionRepository, Validator validator, MessageSourceService messageSource, IOService ioService) {
        this.questionRepository = questionRepository;
        this.validator = validator;
        this.messageSource = messageSource;
        this.ioService = ioService;
    }

    @Override
    public void run() {
        messageSource.printMessage("greeting.warning", null);

        List<Question> questions = questionRepository.read();
        List<String> userAnswers = new ArrayList<>();
        List<String> rightAnswers = getRightAnswers(questions);

        for (Question question : questions) {
            ioService.outputString(question.getText());
            for (Option option : question.getOptions()) {
                ioService.outputString(option.getText());
            }
            userAnswers.add(ioService.readString().trim().toLowerCase(Locale.ROOT));
        }

        validator.validate(rightAnswers, userAnswers);
    }

    private List<String> getRightAnswers(List<Question> questions) {
        return questions.stream().map(Question::getOptions).flatMap(Collection::stream).filter(Option::isRight).map(Option::getText).collect(Collectors.toList());
    }
}

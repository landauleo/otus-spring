package ru.otus.spring.service.provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@DisplayName("Тест на ResourceProvider с возможностью потрогать BDD (given VS. when)")
class ResourceProviderImplTest {

    @MockBean
    private LocaleProvider localeProvider;

    @Autowired
    private ResourceProvider resourceProvider;


    @Test
    @DisplayName("Возвращает нужное название файла для Locale RU")
    void getFilenameRuLocale() {
        given(localeProvider.getLocale()).willReturn(new Locale("ru_RU"));

        assertNotNull(resourceProvider.getFilename());
        assertDoesNotThrow(() -> resourceProvider.getFilename());
        assertEquals("ru_ru_questionnaire.csv", resourceProvider.getFilename());
    }

    @Test
    @DisplayName("Возвращает нужное название файла для Locale EN")
    void getFilenameENLocale() {
        given(localeProvider.getLocale()).willReturn(Locale.ENGLISH);

        assertNotNull(resourceProvider.getFilename());
        assertDoesNotThrow(() -> resourceProvider.getFilename());
        assertEquals("en_questionnaire.csv", resourceProvider.getFilename());
    }

    @Test
    @DisplayName("Пытается зарезолвить fileName даже при отсутствующей Locale")
    void getFilenameNullLocale() {
        given(localeProvider.getLocale()).willReturn(null);

        assertNotNull(resourceProvider.getFilename());
        assertDoesNotThrow(() -> resourceProvider.getFilename());
        assertEquals("null_questionnaire.csv", resourceProvider.getFilename());
    }

}
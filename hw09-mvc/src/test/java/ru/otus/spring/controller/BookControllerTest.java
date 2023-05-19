package ru.otus.spring.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Controller для работы с книгами")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    private static final String TEXT_HTML_UTF8_MIME_TYPE = "text/html;charset=UTF-8";
    private static final String TEXT_FOR_ERROR_PAGE = "Упс.... что-то пошло не так";
    private static final String TEXT_FOR_CREATE_PAGE = "Add a new book in your collection:";
    private static final String TEXT_FOR_LIST_PAGE = "List of all books";
    private static final String TEXT_FOR_EDIT_PAGE = "Edit your book:";
    private static final ObjectId id = new ObjectId("645850e49269b4382292c9b8");

    @Test
    void listPageTest() throws Exception {
        List<Book> books = List.of(new Book(id, "Peter Pan", new Genre("fantasy"), new Author("J. M. Barrie")),
                new Book(id, "Amok", new Genre("novella"), new Author("Stefan Zweig")));

        given(bookService.getAll()).willReturn(books);

        MvcResult mvcResult = mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF8_MIME_TYPE))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains(TEXT_FOR_LIST_PAGE));
        then(bookService).should(times(1)).getAll();
    }

    @Test
    void editPageTest() throws Exception {
        Book book = new Book(id, "Amok", new Genre("novella"), new Author("Stefan Zweig"));
        given(bookService.getById(id)).willReturn(book);

        MvcResult mvcResult = mvc.perform(get("/edit").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF8_MIME_TYPE))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains(TEXT_FOR_EDIT_PAGE));
        then(bookService).should(times(1)).getById(id);
    }

    @Test
    void editBookTest() throws Exception {
        BookDto bookDto = new BookDto(id.toString(), "Amok", "novella", "Stefan Zweig");
        given(bookService.save(id, "Amok", "novella", "Stefan Zweig")).willReturn(id);

        mvc.perform(post("/edit")
                        .flashAttr("bookDto", bookDto)) //ЭТО БЫЛ ДОЛГИЙ ПОИСК
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        then(bookService).should(times(1)).save(id, bookDto.getName(), bookDto.getGenre(), bookDto.getAuthor());
    }

    @Test
    void deleteTest() throws Exception {
        mvc.perform(post("/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        then(bookService).should(times(1)).deleteById(id);
    }

    @Test
    void createPageTest() throws Exception {
        Book book = new Book(id, "Amok", new Genre("novella"), new Author("Stefan Zweig"));
        given(bookService.getById(id)).willReturn(book);

        MvcResult mvcResult = mvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF8_MIME_TYPE))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains(TEXT_FOR_CREATE_PAGE));
    }

    /*
     * В рамках тестирования приложений на Spring Framework с использованием библиотеки MockMvc, flash attributes представляют собой специальную функциональность,
     * которая позволяет передавать данные между запросами внутри одного теста.
     * Обычно, когда вы выполняете HTTP-запросы, каждый запрос обрабатывается независимо от других запросов.
     * Однако, иногда возникает необходимость передать данные или атрибуты от одного запроса к другому в рамках одного теста.
     * Например, вы можете хотеть проверить, что после отправки формы значения полей сохраняются во flash attributes и
     * корректно отображаются на следующей странице.
     * Flash attributes позволяют сохранять данные и передавать их между запросами, моделируя ситуацию, когда пользователь делает переход с одной страницы на другую.
     * Данные, сохраненные в flash attributes, будут доступны только в рамках текущего теста и не будут переноситься между различными тестами.
     */
    @Test
    void createBookTest() throws Exception {
        BookDto bookDto = new BookDto(null, "Amok", "novella", "Stefan Zweig");

        mvc.perform(post("/create")
                        .flashAttr("bookDto", bookDto)) //ЭТО БЫЛ ДОЛГИЙ ПОИСК
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        then(bookService).should(times(1)).save(any(ObjectId.class), eq(bookDto.getName()), eq(bookDto.getGenre()), eq(bookDto.getAuthor()));
    }

    @Test
    void errorPageTest() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF8_MIME_TYPE))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();

        assertTrue(responseContent.contains(TEXT_FOR_ERROR_PAGE));
    }

}
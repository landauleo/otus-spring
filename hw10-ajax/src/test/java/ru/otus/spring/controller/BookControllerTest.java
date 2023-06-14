package ru.otus.spring.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.BookService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Controller для работы с книгами")
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    private static final ObjectId id = new ObjectId("645850e49269b4382292c9b8");

    @Test
    void getAllBooksTest() throws Exception {
        Book book = new Book(id, "Amok", new Genre("novella"), new Author("Stefan Zweig"));
        BookDto bookDto = new BookDto(id.toString(), "Amok", "novella", "Stefan Zweig");
        given(bookService.getAll()).willReturn(List.of(book));

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(bookDto))));

        then(bookService).should(times(1)).getAll();
    }

    @Test
    void editBookTest() throws Exception {
        BookDto bookToEdit = new BookDto(id.toString(), "Unbekannter", "novella", "Stefan Zweig");
        given(bookService.save(id, "Unbekannter", "novella", "Stefan Zweig")).willReturn(id);

        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(bookToEdit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(bookService).should(times(1)).save(id, bookToEdit.getName(), bookToEdit.getGenre(), bookToEdit.getAuthor());
    }

    @Test
    void deleteTest() throws Exception {
        mvc.perform(delete("/api/book/" + id)).andExpect(status().isOk());

        then(bookService).should(times(1)).deleteById(id);
    }

    @Test
    void createBookTest() throws Exception {
        BookDto bookToCreate = new BookDto(null, "Amok", "novella", "Stefan Zweig");

        mvc.perform(post("/api/book")
                        .content(objectMapper.writeValueAsString(bookToCreate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(bookService).should(times(1)).save(any(ObjectId.class), eq(bookToCreate.getName()), eq(bookToCreate.getGenre()), eq(bookToCreate.getAuthor()));
    }

}
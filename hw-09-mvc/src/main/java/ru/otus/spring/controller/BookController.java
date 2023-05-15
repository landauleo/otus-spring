package ru.otus.spring.controller;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.controller.dto.BookDto;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = bookService.getAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        log.info("{} books found", books.size());
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        Book book = bookService.getById(new ObjectId(id));
        model.addAttribute("book", BookDto.toDto(book));
        return "edit";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute(value = "bookDto") BookDto bookDto) {
        bookService.save(new ObjectId(bookDto.getId()), bookDto.getName(), bookDto.getGenre(), bookDto.getAuthor());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        bookService.deleteById(new ObjectId(id));
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("bookDto", new BookDto(
                null,
                "The Girl with the Dragon Tattoo",
                "crime",
                "Stieg Larsson"));
        return "create";
    }

    @PostMapping(value = "/create")
    public String createBook(@ModelAttribute(value = "bookDto") BookDto bookDto) {
        bookService.save(new ObjectId(), bookDto.getName(), bookDto.getGenre(), bookDto.getAuthor());
        return "redirect:/";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

}

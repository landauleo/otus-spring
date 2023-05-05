package ru.otus.spring.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public String listPage(Model model) {
//        List<Book> books = bookService.getAll();
//        model.addAttribute("books", books);
        model.addAttribute("books", List.of(
                new Book(new ObjectId(),
                        "sss",
                        new Genre("sss"),
                        new Author("ssssa"))));
        return "list";
    }

//    @GetMapping("/edit")
//    public String editPage(@RequestParam("id") long id, Model model) {
//        Person person = repository.findById(id).orElseThrow(NotD::new);
//        model.addAttribute("person", person);
//        return "edit";
//    }
//
//    @PostMapping("/edit")
//    public String savePerson(Person person) {
//        repository.save(person);
//        return "redirect:/";
//    }
}

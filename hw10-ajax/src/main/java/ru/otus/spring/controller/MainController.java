package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//не @RestController, чтобы отдавалась именно страничка
public class MainController {

    @GetMapping("/")
    public String getIndexPage(Model model) {
        return "index";
    }

}

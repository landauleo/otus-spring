package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error";
    }

}

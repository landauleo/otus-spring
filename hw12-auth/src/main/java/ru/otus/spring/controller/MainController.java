package ru.otus.spring.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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

    //решение подсмотрено отсюда
    @GetMapping("static/css/style.css")
    @ResponseBody
    public ResponseEntity<String> getStyle() throws IOException {
        // получаем содержимое файла из папки ресурсов в виде потока
        InputStream is = getClass().getClassLoader().getResourceAsStream("static/css/style.css");
        // преобразуем поток в строку
        BufferedReader bf = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = bf.readLine()) != null){
            sb.append(line).append("\n");
        }

        // создаем объект, в котором будем хранить HTTP заголовки
        final HttpHeaders httpHeaders= new HttpHeaders();
        // добавляем заголовок, который хранит тип содержимого
        httpHeaders.add("Content-Type", "text/css; charset=utf-8");
        // возвращаем HTTP ответ, в который передаем тело ответа, заголовки и статус 200 Ok
        return new ResponseEntity<>(sb.toString(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("static/images/{name}")
    public @ResponseBody byte[] getImage(@PathVariable("name") String name) throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("/static/images/" + name);
        return IOUtils.toByteArray(Objects.requireNonNull(in));
    }


}

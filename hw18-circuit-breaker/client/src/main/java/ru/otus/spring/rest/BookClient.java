package ru.otus.spring.rest;

import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book", url = "localhost:8666/")
public interface BookClient {

    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
            },
            fallbackMethod = "defaultGetBooksResponse"
    )
    @GetMapping("/api/book")
    List<BookDto> getBooks();

    @DeleteMapping("/api/book/{id}")
    void deleteById(@PathVariable("id") String id);


    @HystrixCommand(
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3")
            },
            fallbackMethod = "defaultSaveBookResponse"
    )
    @PostMapping(value = "/api/book")
    String saveBook(@RequestBody BookDto bookDto);

    private List<BookDto> defaultGetBooksResponse() {
        return null;
    }

    private String defaultSaveBookResponse() {
        return "I WAS NOT SAVED";
    }

}

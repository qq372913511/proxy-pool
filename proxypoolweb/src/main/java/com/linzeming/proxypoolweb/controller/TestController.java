package com.linzeming.proxypoolweb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <meta charset=\"UTF-8\">\n" +
        "    <title>Title</title>\n" +
        "</head>\n" +
        "<body>\n" +
        "<h1>Hello World!</h1>\n" +
        "<h1>Hello World222!</h1>\n" +
        "<h1>Hello World333!</h1>\n" +
        "<h1>Hello World444!</h1>\n" +
        "<h1>Hello World123!</h1>\n" +
        "<h1>Hello World1234!</h1>\n" +
        "<h1>Hello 123!</h1>\n" +
        "<h1>Hello 123!</h1>\n" +
        "<h1>Hello 23!</h1>\n" +
        "<h1>Hello 123123123!</h1>\n" +
        "</body>\n" +
        "</html>";
    }
}

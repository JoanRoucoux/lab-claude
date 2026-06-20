package com.joanroucoux.labclaude.controller;

import com.joanroucoux.labclaude.model.HelloResponse;
import com.joanroucoux.labclaude.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public HelloResponse hello(@RequestParam(defaultValue = "world") String name) {
        return helloService.sayHello(name);
    }
}
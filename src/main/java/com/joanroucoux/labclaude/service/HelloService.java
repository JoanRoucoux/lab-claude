package com.joanroucoux.labclaude.service;

import com.joanroucoux.labclaude.model.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public HelloResponse sayHello(String name) {
        return new HelloResponse("Hello " + name.strip());
    }
}
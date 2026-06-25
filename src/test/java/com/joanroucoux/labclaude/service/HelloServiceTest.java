package com.joanroucoux.labclaude.service;

import com.joanroucoux.labclaude.model.HelloResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloServiceTest {

    private final HelloService service = new HelloService();

    @Test
    void shouldReturnHelloMessage() {
        HelloResponse response = service.sayHello("Joan");

        assertEquals("Hello Joan", response.message());
    }

    @Test
    void shouldStripWhitespacePadding() {
        HelloResponse response = service.sayHello("  Alice  ");

        assertEquals("Hello Alice", response.message());
    }
}
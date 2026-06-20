package com.joanroucoux.labclaude.controller;

import com.joanroucoux.labclaude.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.joanroucoux.labclaude.model.HelloResponse;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HelloService helloService;

    @Test
    void shouldReturnHello() throws Exception {

        when(helloService.sayHello("world"))
                .thenReturn(new HelloResponse("Hello world"));

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello world"));
    }

    @Test
    void shouldReturnCustomName() throws Exception {

        when(helloService.sayHello("Joan"))
                .thenReturn(new HelloResponse("Hello Joan"));

        mockMvc.perform(get("/hello?name=Joan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Joan"));
    }
}
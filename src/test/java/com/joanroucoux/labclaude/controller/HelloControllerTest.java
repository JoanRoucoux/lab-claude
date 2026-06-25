package com.joanroucoux.labclaude.controller;

import com.joanroucoux.labclaude.model.HelloResponse;
import com.joanroucoux.labclaude.service.HelloService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void shouldRejectBlankNameViaGet() throws Exception {

        // ?name= triggers defaultValue="world"; whitespace-only bypasses it and reaches @NotBlank
        mockMvc.perform(get("/hello").param("name", "   "))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectNameTooLongViaGet() throws Exception {

        mockMvc.perform(get("/hello?name=" + "a".repeat(51)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnHelloOnPost() throws Exception {

        when(helloService.sayHello("Joan"))
                .thenReturn(new HelloResponse("Hello Joan"));

        mockMvc.perform(post("/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Joan\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello Joan"));
    }

    @ParameterizedTest
    @MethodSource("invalidPostBodies")
    void shouldRejectInvalidPostName(String body) throws Exception {

        mockMvc.perform(post("/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    private static Stream<String> invalidPostBodies() {
        return Stream.of(
                "{\"name\":\"\"}",
                "{\"name\":\"" + "a".repeat(51) + "\"}",
                "{}"
        );
    }
}

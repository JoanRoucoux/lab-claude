package com.joanroucoux.labclaude.controller;

import com.joanroucoux.labclaude.model.ErrorResponse;
import com.joanroucoux.labclaude.model.HelloRequest;
import com.joanroucoux.labclaude.model.HelloResponse;
import com.joanroucoux.labclaude.service.HelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hello", description = "Greeting operations")
@Validated
@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @Operation(summary = "Get a greeting", responses = {
            @ApiResponse(responseCode = "200", description = "Successful greeting"),
            @ApiResponse(responseCode = "400", description = "Invalid name",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/hello")
    public HelloResponse hello(
            @Parameter(description = "Name to greet (max 50 characters, must not be blank)")
            @RequestParam(defaultValue = "world") @NotBlank @Size(max = 50) String name) {
        return helloService.sayHello(name);
    }

    @Operation(summary = "Post a greeting with body validation", responses = {
            @ApiResponse(responseCode = "200", description = "Successful greeting"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/hello")
    public HelloResponse hello(@Valid @RequestBody HelloRequest request) {
        return helloService.sayHello(request.name());
    }
}

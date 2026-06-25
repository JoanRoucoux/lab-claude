package com.joanroucoux.labclaude.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Greeting response")
public record HelloResponse(
        @Schema(description = "The greeting message", example = "Hello Joan")
        String message
) {
}

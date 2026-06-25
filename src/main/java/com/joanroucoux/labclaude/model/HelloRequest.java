package com.joanroucoux.labclaude.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for the greeting endpoint")
public record HelloRequest(
        @Schema(description = "Name to greet", example = "Joan")
        @NotBlank
        @Size(max = 50)
        String name
) {
}

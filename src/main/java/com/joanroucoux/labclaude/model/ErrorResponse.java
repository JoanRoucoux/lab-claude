package com.joanroucoux.labclaude.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response")
public record ErrorResponse(
        @Schema(description = "Description of the validation error")
        String message
) {
}

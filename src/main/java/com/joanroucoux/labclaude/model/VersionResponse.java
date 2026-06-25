package com.joanroucoux.labclaude.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Version response")
public record VersionResponse(
        @Schema(description = "Application version", example = "0.0.1-SNAPSHOT")
        String version
) {
}

package com.joanroucoux.labclaude.controller;

import com.joanroucoux.labclaude.model.VersionResponse;
import com.joanroucoux.labclaude.service.VersionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Version", description = "Application version")
@RestController
public class VersionController {

    private final VersionService versionService;

    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @Operation(summary = "Get the current application version", responses = {
            @ApiResponse(responseCode = "200", description = "Current version")
    })
    @GetMapping("/version")
    public VersionResponse version() {
        return versionService.getVersion();
    }
}

package com.joanroucoux.labclaude.service;

import com.joanroucoux.labclaude.model.VersionResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionServiceTest {

    private final VersionService service = new VersionService("1.2.3");

    @Test
    void shouldReturnConfiguredVersion() {
        VersionResponse response = service.getVersion();

        assertEquals("1.2.3", response.version());
    }
}

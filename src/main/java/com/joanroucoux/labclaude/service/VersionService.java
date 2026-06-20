package com.joanroucoux.labclaude.service;

import com.joanroucoux.labclaude.model.VersionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VersionService {

    private final String version;

    public VersionService(@Value("${app.version}") String version) {
        this.version = version;
    }

    public VersionResponse getVersion() {
        return new VersionResponse(version);
    }
}

package com.joanroucoux.labclaude.controller;

import com.joanroucoux.labclaude.model.VersionResponse;
import com.joanroucoux.labclaude.service.VersionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VersionController.class)
class VersionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VersionService versionService;

    @Test
    void shouldReturnVersion() throws Exception {

        when(versionService.getVersion())
                .thenReturn(new VersionResponse("1.2.3"));

        mockMvc.perform(get("/version"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value("1.2.3"));
    }
}

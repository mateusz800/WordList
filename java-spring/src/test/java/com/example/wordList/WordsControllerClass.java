package com.example.wordList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;


import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WordsControllerClass {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldReturnWordsFrequencyList() throws Exception {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "Hello, World!".getBytes()
        );
        //noinspection ResultOfMethodCallIgnored
        assertThat(mockMvc.perform(multipart("/wordList").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hello", is(1)))
                .andExpect(jsonPath("$.world", is(1)))
        );
    }

}

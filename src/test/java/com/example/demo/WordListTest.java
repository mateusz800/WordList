package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
class WordListTest {

    private final MockMultipartFile file
            = new MockMultipartFile(
            "test_file",
            "Kot ali ali ali ali. Ala ma kot, kot ma Ala, Ala. kot ,kot. .ala?".getBytes()
    );

    @Autowired
    MyService myService;


    @Test
    void countWordFrequency() throws IOException {
        Map<String, Long> result = myService.calcWordFreqList(file);
        assertThat(result.size()).isEqualTo(4);
        assertThat(result.get("ala")).isEqualTo(4);
        assertThat(result.get("kot")).isEqualTo(5);
        assertThat(result.get("ma")).isEqualTo(2);
        assertThat(result.get("ali")).isEqualTo(4);
    }

    @Test
    void sortByOccurrences() throws IOException {
        Map<String, Long> result = myService.calcWordFreqList(file);
        String[] keys = result.keySet().toArray(new String[0]);
        assertThat(keys[0]).isEqualTo("kot");
        assertThat(keys[1]).isEqualTo("ala");
        assertThat(keys[2]).isEqualTo("ali");
        assertThat(keys[3]).isEqualTo("ma");
    }

    @Test
    void emptyFileShouldReturnEmptyMap() throws IOException {
        MockMultipartFile emptyFile = new MockMultipartFile("empty", new byte[0]);
        Map<String, Long> result = myService.calcWordFreqList(emptyFile);
        assertThat(result.keySet().size()).isEqualTo(0);
    }
}

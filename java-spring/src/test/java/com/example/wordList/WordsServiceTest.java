package com.example.wordList;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class WordsServiceTest {

    @Autowired
    WordsService wordsService;

    @Test
    void countWordFrequency() {
        Map<String, Long> result = wordsService.calcWordFreqList("Ala ma kot, kot ma Alę".lines());
        assertThat(result.get("ala")).isEqualTo(1);
        assertThat(result.get("kot")).isEqualTo(2);
        assertThat(result.get("ma")).isEqualTo(2);
    }

    @Test
    void sortByOccurrencesAndThenAlphabetical() {
        Map<String, Long> result = wordsService.calcWordFreqList("Ala ma kot, kot ma. KOT ALA".lines());
        String[] keys = result.keySet().toArray(new String[0]);
        assertThat(keys[0]).isEqualTo("kot");
        assertThat(keys[1]).isEqualTo("ala");
        assertThat(keys[2]).isEqualTo("ma");
    }

    @Test
    void detectWordsWithPolishCharacters() {
        Map<String, Long> result = wordsService.calcWordFreqList("żółw".lines());
        assertThat(result.get("żółw")).isEqualTo(1);
    }
}

package com.example.wordList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.ToLongFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import one.util.streamex.StreamEx;

@Service("wordsService")
public class WordsService {

    private static final Comparator<Map.Entry<String, Long>> wordOccurrencesComparator =
            Comparator.comparingLong((ToLongFunction<Map.Entry<String, Long>>) Map.Entry::getValue)
                    .reversed()
                    .thenComparing(Map.Entry::getKey);

    // TODO: accept more special characters than only the polish ones
    private static final Pattern regexPattern = Pattern.compile("(?i)[a-ząćęłńóśźż]+");

    public Stream<String> openFileStream(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            return reader.lines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }


    public Map<String, Long> calcWordFreqList(Stream<String> textStream) {
        return StreamEx.of(
                StreamEx.of(textStream)
                        .flatMap(line -> regexPattern.matcher(line)
                                .results()
                                .map(result -> result.group().toLowerCase()))
                        .groupingBy(word -> word, Collectors.counting()))
                .flatMap(map -> map.entrySet()
                        .stream()
                        .sorted(wordOccurrencesComparator)
                ).collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}

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

@Service("myService")
public class MyService {

    public Map<String, Long> calcWordFreqList(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Pattern regexPattern = Pattern.compile("[a-zA-Z]+");

        // Compare words descending by occurrences and then by word name
        Comparator<Map.Entry<String, Long>> wordOccurrencesComparator =
                Comparator.comparingLong((ToLongFunction<Map.Entry<String, Long>>) Map.Entry::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey);

        // Process file
        LinkedHashMap<String, Long> frequencyMap = reader.lines()
                .flatMap(line -> regexPattern.matcher(line)
                        .results()
                        .map(result -> result.group().toLowerCase()))
                .collect(Collectors.collectingAndThen(
                        Collectors.groupingBy(
                                word -> word,
                                Collectors.counting()
                        ), map -> map.entrySet().stream()
                                .sorted(wordOccurrencesComparator)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new))
                ));

        inputStream.close();
        return frequencyMap;
    }
}

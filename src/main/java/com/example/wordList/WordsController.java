package com.example.wordList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.stream.Stream;

@RestController
public class WordsController {

    @Autowired
    WordsService wordsService;

    @PostMapping(path = "/wordList")
    public ResponseEntity<?> calcWorldList(@RequestParam("file") MultipartFile file){
        try {
            Stream<String> inputStream = wordsService.openFileStream(file);
            Map<String, Long> result = wordsService.calcWordFreqList(inputStream);
            inputStream.close();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

# Word List API

API with single endpoint that returns a list of words in posted txt file ordered (descending) by 
frequency of occurrences. Project created using Spring Boot framework.

## Running the application locally
```bash
./gradlew bootRun
```

## Endpoint calling
**URL**: `/wordList`\
**Method**: `POST`\
**Parameters**: 
* *file* - txt file

\
**An example of calling an endpoint using curl:**
```bash
curl localhost:8080/wordList -F file=@./file.txt
```

Sample response:
```
{
    "leg": 7,
    "excitement": 6,
    "ball": 5,
}
```

## Running tests
``` 
./gradlew test
```







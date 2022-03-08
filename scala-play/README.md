# Word List API

[![Scala CI](https://github.com/mateusz800/WordList/actions/workflows/scala.yml/badge.svg)](https://github.com/mateusz800/WordList/actions/workflows/scala.yml)

API with single endpoint that returns a list of words in posted txt file ordered (descending) by
frequency of occurrences. Project created using Scala and Play framework.

## Running the application locally
```bash
sbt run
```

## Endpoint calling
**URL**: `/wordList`\
**Method**: `POST`\
**Parameters**:
* *file* - txt file

\
**An example of calling an endpoint using curl:**
```bash
curl localhost:9000/wordList -F file=@./file.txt
```

Sample response:
```
{
    "leg": 7,
    "excitement": 6,
    "ball": 5,
}
```

## Running test
```bash
sbt run
```







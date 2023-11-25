# moyyn-recruiting


## Architecture

Stacked layer architecture

    HTML
    Controller : CandidateController.java
    The webapp provides two REST post endpoints to demonstrate the following use-cases:

    - POST http://localhost:8080/jackson to:
        upload a PDF file da HTML, 
        extract the values, 
        format a Candidate.java bean
        return it in JSON format by the Jackson library in spring boot

    - POST http://localhost:8080//chatgpt to:
        upload a PDF file da HTML, 
        extract the values, 
        format a Candidate.java bean
        call chatGPT passing the candidate.toString() value which will return it in JSON format and returned as String to the HTTP post caller

## Code repository

    https://github.com/nicholassignore/moyyn-recruiting.git

## Prerequisites

    Java version 17
    Maven 3.8.3
    git

## How to build

    mvn clean package

## How to run the backend
    
    mvn spring-boot:run 

## Verify health of backend

    http://localhost:8080/actuator/health

## How to run the frontend

    From intellij goto: resources/templates and click on the Chrome (or any other browser) button

## ChatGPT integration

The curl to define the question to ask is the following:

$ curl https://api.openai.com/v1/chat/completions \
-H "Content-Type: application/json" \
-H "Authorization: Bearer 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" \
-d '{
"model": "gpt-3.5-turbo",
"messages": [{"role": "user", "content": "can you translate the string \"Candidate(fileName=file, fileType=application/pdf, firstName=mickey, lastName=mouse, age=25, married=true, skills=[java, spring, react])\" to a json string ?"}]
}'

$ curl https://api.openai.com/v1/chat/completions \
-H "Content-Type: application/json" \
-H "Authorization: Bearer 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" \
-d '{
"model": "gpt-3.5-turbo",
"messages": [{"role": "user", "content": "can you translate the string \"Candidate(fileName=file, fileType=application/pdf, firstName=mickey, lastName=mouse, age=25, married=true, skills=[java, spring, react])\" to a json string ?"}]
}'

    - POST http://localhost:8080//chatgpt to:
        upload a PDF file da HTML, 
        extract the values, 
        format a Candidate.java bean
        call chatGPT passing the candidate.toString() value which will return it in JSON format and returned as String to the HTTP post caller



The string embeddded in the message is the java candidate.toString() string. 

ChatGPT will respond back with the equivalent JSON


##  Known imitation 

The name of this example is composed only by first and last name. SOme changes are required if the name is composed on more than one word: in this case we need to concatenate the names into ine single string.  Not really critical for this example. 

For instance:  Nicholas Santos Signore. 


## DEMO

1. far partire la app 
2. val alla pagina uploadFile.hml, run in intellij e carica goofy.pdf e mickeymouse.pdf
3. Postman ed import il workspace sotto test/resources



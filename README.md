# button-and-field

Web application written ENTIRELY in Java, front-end developed using the Vaadin framework.

It contains an input field and a button 
(by pressing the button, the value in the field increases by 1. The field value can be changed manually by entering the desired value.) 

Changes are saved in the database H2 automatically with each change and displayed in the history table.

## Instructions for launching the application
For the application to work correctly, Docker must be installed on the computer. Both services are started with the following command:

```Bash
mvn clean package -Pproduction
docker-compose up
```

Form address http://localhost:8080

## Technologies which I use:
Java 17, Spring Boot 3, Sprting Data, Vaadin 24, Docker, Maven, H2.

#RestAssured Assignment
API test automation using Rest-Assured, Java, Maven, Junit, github and circleci

#Requirements:

Below dependencies needs to be installed/configured
- Java 8 or higher as the programming language
- Maven as build tool
- IDE (IntelliJ is preferred)
- Junit as the testing framework


# API Integration tests

API tests give assurance over the application by running tests that span across multiple microservices and process flow.

## Running tests locally

mvn clean install verify  # defaults to QA enviroment
mvn clean install verify -Denv=staging # test to run staging enviroment (as an example)


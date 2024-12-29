# HRM Test Automation

This project is a test automation suite for the HRM (Human Resource Management) application using Selenium WebDriver and JUnit 5. The tests are written in Java and managed using Maven.

## Prerequisites

- Java 17
- Maven
- Chrome browser

## Setup

1. Clone the repository:
    ```sh
    git clone <repository-url>
    cd hrm-test-automation
    ```

2. Install dependencies:
    ```sh
    mvn clean install
    ```

## Running Tests

To run the tests, use the following command:
```sh
mvn test
```

## Project Structure
- src/test/java/io/akasht/hrm/automation/com/HRMAppTest.java: Contains the test cases for the HRM application.
- pom.xml: Maven configuration file with dependencies and build plugins.

## Test Cases
### HRMAppTest
- testHomePageTitle: Verifies the title of the home page.
- testLogin: Tests the login functionality.
- testInfo: Tests the information page.
- testClaimSubmit: Tests the claim submission functionality.

## Dependencies
- Selenium WebDriver
- JUnit 5
- WebDriverManager 

## Configuration
The project uses Maven for dependency management and build automation. The pom.xml file includes the necessary dependencies and plugins.  

## .gitignore
The `.gitignore` file is configured to exclude build directories, IDE-specific files, and other unnecessary files from version control.
# Selenium Java Testing Example

This repository contains an example of using Selenium WebDriver with Java for automated testing.

## Prerequisites

- Java Development Kit (JDK) 21 or higher
- Apache Maven
- An IDE such as IntelliJ IDEA or Eclipse

## Getting Started

### 1. Clone the Repository

```sh
git clone https://https://github.com/AtamCow/InternThanhVu.git
cd InternThanhVu
```

### 2. Set Up Your Environment
```sh
java -version
mvn -version
```

### 3. Check Dependencies
Check dependencies in your pom.xml file.

### 4. Open 1 test file
Open 1 test class in InternThanhVu\SeleniumJavaBasic\src\test\java\chapter10\

### 5. Running the Tests
To run the tests, use the following command:
```sh
mvn test
```

# Setting up and Running Selenium Grid

## Introduction
Selenium Grid is a tool that allows you to run Selenium tests concurrently on multiple machines and different browsers. This helps speed up test execution and scale your web application testing capabilities.

## Installing Selenium Grid

### Step 1: Download Selenium Server Standalone
Download Selenium Server Standalone from the Selenium official website: [Download Selenium](https://www.selenium.dev/downloads/). Choose the version that matches your operating system.

### Step 2: Starting Selenium Grid Hub
After downloading, open a terminal or command prompt and navigate to the directory containing Selenium Server Standalone. Start the Selenium Grid Hub with the following command:
```sh
java -jar selenium-server-4.21.0.jar hub
```

### Step 3: Starting Selenium Grid Node
On the machines you want to use as Nodes, open a terminal or command prompt and run the following command:
```sh
java -jar selenium-server-4.21.0.jar node -hub http://<Hub-IP>:4444/grid/register
```

### Step 4: Running test
- If you are using an IDE such as IntelliJ or Eclipse, you can run directly from the IDE.
- If you are using Maven, run the tests with the following command in the terminal or command prompt:
```sh
mvn test
```

# Selenium Test Suite 
## Running Tests on Different Browsers

### Prerequisites
- Maven installed
- Java development kit (JDK) installed

### Steps
1. Clone the repository: `git clone <repository-url>`
2. Navigate to the project directory: `cd <project-directory>`
3. Run tests on Chrome:
```
mvn clean test

```
4. Run tests on Firefox:
- Uncomment Firefox setup in `BaseTest.java`
- Run tests again:
  ```
  mvn clean test
  ```
### Notes
- WebDriverManager dynamically manages browser driver versions, ensuring compatibility.
- Ensure Maven dependencies are correctly set up in your `pom.xml`

Here are the detailed contents of the `README.md` file to help you get started with automated testing using Selenium and Java.
This README file provides users with a detailed guide on installing, configuring, and running Selenium Grid to automate web application testing.







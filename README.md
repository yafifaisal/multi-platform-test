# 🌲 krisdemo - Automated UI Testing Framework

Welcome to **krisdemo**, an automated UI testing framework designed using Java, Selenium WebDriver, TestNG, and Cucumber BDD. This project is tailored for robust test automation in web applications, integrated with **Allure Reports**, **Jenkins CI/CD**, and **GitHub**.

---

## 🧰 Technology Stack

- **Language**: Java 21
- **Automation Tool**: Selenium WebDriver 4.31.0
- **Test Runner**: TestNG
- **BDD Framework**: Cucumber 7.15.0 (Cucumber-TestNG integration)
- **Reporting**:
  - Cucumber HTML Report
  - Allure Report
  - Screenshots on Failure
- **Build Tool**: Maven 3.9.9
- **CI/CD**: Jenkins (Declarative Pipeline)
- **Logging**: SLF4J + Logback
- **Headless Support**: Configurable via Jenkinsfile
- **Retry Mechanism**: Custom TestNG retry listener

---

## 🏁 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yafifaisal/krisdemo.git
cd krisdemo
```

### 2. Prerequisites

- Java 21
- Maven 3.9.9
- Chrome (latest version)
- Install Allure CLI (for local report viewing)

```bash
brew install allure
```

### 3. Execute Tests Locally

```bash
mvn clean test -Dbrowser=chrome -Dcucumber.filter.tags="@smoke"
```

### 4. View Allure Report

```bash
allure serve target/allure-results
```

---

## 🧪 Test Execution & Structure

### Folder Structure

```
krisdemo/
├── pom.xml
├── testng.xml
├── Jenkinsfile
├── src/
│   ├── main/
│   │   └── java/
│   └── test/
│       ├── java/
│       │   ├── stepDefinitions/
│       │   ├── pages/
│       │   ├── hooks/
│       │   ├── runners/
│       │   └── utilities/
│       └── resources/
│           ├── features/
│           └── test-data/
│               └── files/
│               └── tempFiles/
└── target/
```

### Feature File Syntax

```gherkin
@smoke @positive
Scenario: Upload a valid PDF
  Given I am successfully logged in with user "file electronic record user"
  And I upload "new" document "sample.pdf"
  Then Assert that dialog message equal with "The record is registered successfully."
```

---

## 📷 Screenshots on Failure

- Captured in `src/test/reports/screenshots/`
- Automatically saved & embedded into Allure report

---

## 📊 Reporting

### Cucumber HTML

- Path: `target/cucumber-reports/cucumber.html`

### Allure Report

- JSON & environment metadata stored in: `target/allure-results`
- Served using:
  ```bash
  allure serve target/allure-results
  ```

---

## 🤖 Jenkins Pipeline Setup

### Jenkinsfile Highlights

- Parameterized build: browser, tags, headless
- GitHub trigger via `githubPush()`
- Stages:
  1. Checkout
  2. Build & Test
  3. Allure Report
  4. (Post) HTML Cucumber Report + JUnit results

### Plugins Required:

- GitHub Integration
- Allure Jenkins Plugin
- HTML Publisher Plugin
- Maven Integration Plugin
- JDK Tool Installer

---

## 🌐 Environment Config

- Java: OpenJDK 21
- Maven: 3.9.9
- ChromeDriver managed by WebDriverManager
- Browser & environment variables configurable via:
  ```groovy
  sh "mvn test -Dbrowser=chrome -Dheadless=false -Dcucumber.filter.tags='@smoke'"
  ```

---

## 🧼 Cleanup & File Handling

- Uploaded test files renamed with millisecond timestamp
- Temporarily saved in `/test-data/tempFiles`
- Automatically deleted in `@AfterAll` via `FileHelper.cleanUpTempFiles()`

---

## Video Demonstration

https://drive.google.com/file/d/1B3oMWq-rdCxuL3dwqcd6ygSDR9FZ6wTu/view?usp=sharing

---

## 🤝 Contribution & Credits

Maintained by **Faisal Yafi**.

---

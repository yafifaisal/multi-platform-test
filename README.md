# 🌲 multi-platform-test - Automated Testing Framework

**multi-platform-test** is a comprehensive, scalable test automation framework built using **Java**, with a structured **Cucumber BDD** approach and powered by **TestNG**.

This framework is designed for end-to-end testing across multiple platforms:

- ✅ **Web UI Testing** with [Selenium WebDriver](https://www.selenium.dev/)
- 📱 **Mobile App Testing** using [Appium](https://appium.io/)
- 🌐 **API Testing** powered by [Rest Assured](https://rest-assured.io/)

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
git clone https://github.com/yafifaisal/multi-platform-test.git
cd multi-platform-test
```

📱 Mobile App Setup
⚠️ Note: To keep the repository lightweight and comply with GitHub’s 100MB file size limit, the mobile APK is not included in the source code.

🔽 Step 1: Download the APK
Download the latest version of the mobile APK manually from your preferred location (e.g., CI artifact, Google Drive, etc.).
Download Link: https://trustwallet.com/id

📁 Step 2: Place the APK in the Required Directory
After downloading the APK, place it in the following path inside the project:
`src/test/resources/mobile/app/latest.apk`

This path is used in the mobile test capabilities to launch the app during automation runs.

### 2. Prerequisites

- Java 21
- Maven 3.9.9
- Chrome (latest version)
- Install Allure CLI (for local report viewing)

```bash
brew install allure
```

### 3. Execute Tests Locally

#### Web

```bash
mvn clean test -Pweb -Dplatform=web
```

#### Mobile

```bash
mvn clean test -Pmobile -Dplatform=mobile
```

#### API

```bash
mvn clean test -Papi -Dplatform=api
```

### 4. View Cucumber Report

- Go to `target` folder
- Open cucumber-html report

<img width="1460" height="646" alt="Screenshot 2025-07-19 at 22 22 28" src="https://github.com/user-attachments/assets/ffb5fd5f-5939-4797-8231-29c65b558ce9" />


---

## 🧪 Test Execution & Structure

### Folder Structure

```
multi-platform-test/
├── src/
│   ├── main/java/
│   │   ├── utilities/
│   │   └── helpers/
│   └── test/java/
│       ├── api/
│       │   ├── stepDefinitions/
│       │   ├── hooks/
│       │   └── runners/
│       ├── mobile/
│       │   ├── drivers/
│       │   ├── stepDefinitions/
│       │   ├── hooks/
│       │   └── runners/
│       └── web/
│           ├── drivers/
│           ├── stepDefinitions/
│           ├── hooks/
│           └── runners/
│
├── src/test/resources/
│   ├── api/
│   │   ├── features/
│   │   └── config.properties
│   ├── mobile/
│   │   ├── features/
│   │   └── config.properties
│   └── web/
│       ├── features/
│       ├── test-data/
│       └── config.properties
```

### Feature File Syntax

```gherkin
@smoke
Feature: Test select date picker and upload image

    Scenario: Test select date picker and upload image
        Given I am on the registration page
        And I submit the registration form with dummy data
        When I upload a single file named "sample.pdf"
        And I upload multiple files
            | file            |
            | sample.pdf      |
            | sample copy.pdf |
        Then I verify exactly 2 files are uploaded
```

---

## 📷 Screenshots on Failure

- Captured in `src/test/reports/screenshots/${platform}`
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

https://drive.google.com/file/d/1t_iIzVGd9vqV68k7Vyye7ArVhelswyBm/view?usp=sharing

---

## 🤝 Contribution & Credits

Maintained by **Faisal Yafi**.

---

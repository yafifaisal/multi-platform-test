pipeline {
    agent any

    parameters {
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'Browser to use')
        string(name: 'TAGS', defaultValue: '@smoke', description: 'Cucumber Tags')
    }

    tools {
        jdk 'OpenJDK 21'
        maven 'maven-3.9.9'
    }

    environment {
        GIT_REPO_URL = 'https://github.com/yafifaisal/krisdemo.git'
        GIT_CREDENTIALS_ID = '1b260b41-27a1-411c-951d-1ebae23c2270'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', credentialsId: env.GIT_CREDENTIALS_ID, url: env.GIT_REPO_URL
            }
        }

        stage('Build & Test') {
            steps {
                sh "mvn clean test -Dbrowser=${params.BROWSER} -Dcucumber.filter.tags='${params.TAGS}'"
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        success {
            echo '✅ Tests passed successfully!'
        }
        failure {
            echo '❌ Some tests failed!'
        }
    }
}

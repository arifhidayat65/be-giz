pipeline {
    agent any

    environment {
        // Define credentials ID for GitHub
        GITHUB_CREDENTIALS = credentials('ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCxZ97x/oHMtsGUCzr7cuE5na19olZvykv9zS9hJM5GBSadvVDccglzjaFiaNSF3K1JxMuZujCJ8ZLi7LAtjfRxxGtuiP16DqjosUnfM+Pp9+jkD6jFAUXYuA0WeuiNpPdIfoIEBV6AmyMdhCRwMFe1SWQdp6L/70q9a3M57/gveNovqtluIMpfIu5iep2GMIC3euM/AEH4qRC+ploZzUqePyNB0PQb3PJ4KyKnFPEm4kw0S9/tiqZmW2Pe5TW+pHqoOtVYFeX3XmAQnmR2rQrVgn+Uck9ZdZlLD+TaWMWo9+tPM6xOYnHvE6V/2hUMgLIUqVWgGpFvPrLywUMQQYE+tsNFxm4jOjYYZT5DdR8D6kJgOsk8Nb+AmqJGcDq7BQxvlMR6mL8T5HuZMmVa2M8/ftYMCKHjJfAsNjBGoSX3tGtA0onqFWP6moc6F+Vw7kwHch3oQsUSGkQDnqGGGwz7GfkBhEo+bAYCicCV9WttbfN52biN4N3PMjUp9ToDTlW11mD+vg33VlMS66tDybJ9zNIAWdWjCiR3R+a0p4gZOvxEcRZL2YakOFb/5sJDYpiVauiF+WaTbMxS6dNfdDRvY+ns9qgBVQMfD9EVBOKt3gemhFIV5yllxVCOh8PAEBjbvgogeuIxh8UoR9RE+ExIZZBYeXN6oj/tXSE7i22tgQ== arifhidayat1010@gmail.com
')
        GITHUB_REPO = 'git@github.com:arifhidayat65/be-giz.git'
        BRANCH = 'main'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clean workspace before building
                cleanWs()
                // Checkout code from GitHub with SSH
                sshagent(['github-credentials']) {
                    git branch: env.BRANCH,
                        url: env.GITHUB_REPO
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Make mvnw executable
                    sh 'chmod +x mvnw'
                    // Clean and package the application
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run tests
                    sh './mvnw test'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t gizmap-app .'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Stop existing containers
                    sh 'docker-compose down || true'
                    
                    // Deploy the application
                    sh 'docker-compose up -d'
                }
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build or deployment failed.'
        }
        always {
            // Clean up workspace
            cleanWs()
        }
    }
}

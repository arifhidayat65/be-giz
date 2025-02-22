pipeline {
    agent any

    environment {
        // Define credentials ID for GitHub
        GITHUB_CREDENTIALS = credentials('github-credentials')
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

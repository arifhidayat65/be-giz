pipeline {
    agent any

    environment {
        GITHUB_REPO = 'git@github.com:arifhidayat65/be-giz.git'
        BRANCH = 'master'
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                sshagent(['github-credentials']) {
                    git branch: env.BRANCH,
                        url: env.GITHUB_REPO,
                        credentialsId: 'github-credentials'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'chmod +x mvnw'
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh './mvnw test'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t gizmap-app .'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'docker-compose down || true'
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
            cleanWs()
        }
    }
}

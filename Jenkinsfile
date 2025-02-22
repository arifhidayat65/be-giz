pipeline {
    agent any

    environment {
        GITHUB_REPO = 'https://github.com/arifhidayat65/be-giz.git'
        BRANCH = 'master'
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                git branch: env.BRANCH,
                    url: env.GITHUB_REPO,
                    credentialsId: 'github-credentials'
            }
        }

        stage('Build') {
            steps {
                script {
                    try {
                        // Debug: List directory contents
                        sh 'ls -la'
                        
                        // Ensure mvnw exists and is executable
                        sh '''
                            if [ ! -f "mvnw" ]; then
                                echo "Maven wrapper not found. Creating it..."
                                mvn -N wrapper:wrapper
                            fi
                            chmod +x mvnw
                        '''
                        
                        // Build with debug output
                        sh './mvnw clean package -DskipTests -X'
                        
                        // Verify target directory and JAR file
                        sh '''
                            echo "Checking target directory..."
                            ls -la target/
                        '''
                    } catch (Exception e) {
                        echo "Build failed: ${e.getMessage()}"
                        currentBuild.result = 'FAILURE'
                        error("Build stage failed")
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    try {
                        sh './mvnw test'
                    } catch (Exception e) {
                        echo "Tests failed: ${e.getMessage()}"
                        currentBuild.result = 'FAILURE'
                        error("Test stage failed")
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        sh 'docker build -t gizmap-app .'
                    } catch (Exception e) {
                        echo "Docker build failed: ${e.getMessage()}"
                        currentBuild.result = 'FAILURE'
                        error("Docker build stage failed")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    try {
                        sh 'docker-compose down || true'
                        sh 'docker-compose up -d'
                    } catch (Exception e) {
                        echo "Deployment failed: ${e.getMessage()}"
                        currentBuild.result = 'FAILURE'
                        error("Deploy stage failed")
                    }
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

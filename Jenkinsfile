pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 17'
    }

    environment {
        GITHUB_REPO = 'https://github.com/arifhidayat65/be-giz.git'
        BRANCH = 'master'
        DOCKER_IMAGE = 'gizmap-app'
    }

    stages {
        stage('Environment Info') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "JAVA_HOME = ${JAVA_HOME}"
                    java -version
                    mvn -version
                    docker version
                '''
            }
        }

        stage('Checkout') {
            steps {
                cleanWs()
                git branch: env.BRANCH,
                    url: env.GITHUB_REPO,
                    credentialsId: 'github-credentials'
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    try {
                        sh '''
                            echo "Building with Maven..."
                            mvn clean package -DskipTests
                            
                            echo "Running tests..."
                            mvn test
                            
                            echo "Verifying target directory..."
                            ls -la target/
                        '''
                    } catch (Exception e) {
                        echo "Build or test failed: ${e.getMessage()}"
                        error("Build and test stage failed")
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    try {
                        sh '''
                            echo "Building Docker image..."
                            docker build -t ${DOCKER_IMAGE} .
                            
                            echo "Verifying Docker image..."
                            docker images | grep ${DOCKER_IMAGE}
                        '''
                    } catch (Exception e) {
                        echo "Docker build failed: ${e.getMessage()}"
                        error("Docker build stage failed")
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    try {
                        sh '''
                            echo "Stopping existing containers..."
                            docker-compose down || true
                            
                            echo "Starting new containers..."
                            docker-compose up -d
                            
                            echo "Verifying containers..."
                            docker-compose ps
                            
                            echo "Checking container logs..."
                            docker-compose logs --tail=50
                        '''
                    } catch (Exception e) {
                        echo "Deployment failed: ${e.getMessage()}"
                        error("Deploy stage failed")
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished'
            cleanWs()
        }
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo '''
                Build or deployment failed!
                Cleaning up...
            '''
            sh 'docker-compose down || true'
        }
    }
}

pipeline {
    agent any

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
                    echo "Workspace = ${WORKSPACE}"
                    java -version
                '''
            }
        }

        stage('Checkout') {
            steps {
                cleanWs()
                git branch: env.BRANCH,
                    url: env.GITHUB_REPO,
                    credentialsId: 'github-credentials'
                
                // Ensure .mvn directory exists and has proper permissions
                sh '''
                    if [ ! -d ".mvn" ]; then
                        echo "Creating .mvn directory structure..."
                        mkdir -p .mvn/wrapper
                        chmod -R 755 .mvn
                    fi
                '''
            }
        }

        stage('Prepare Maven') {
            steps {
                script {
                    try {
                        sh '''
                            echo "Checking Maven wrapper..."
                            if [ ! -f "mvnw" ]; then
                                echo "Maven wrapper not found, downloading it..."
                                mvn -N io.takari:maven:0.7.7:wrapper
                            fi
                            
                            echo "Setting execute permissions..."
                            chmod +x mvnw
                            chmod +x .mvn/wrapper/maven-wrapper.jar || true
                            
                            echo "Verifying Maven wrapper..."
                            ./mvnw --version
                        '''
                    } catch (Exception e) {
                        echo "Maven preparation failed: ${e.getMessage()}"
                        error("Maven preparation stage failed")
                    }
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    try {
                        sh '''
                            echo "Building with Maven wrapper..."
                            ./mvnw clean package -DskipTests
                            
                            echo "Running tests..."
                            ./mvnw test
                            
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

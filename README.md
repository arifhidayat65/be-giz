# Gizmap Application

## Jenkins Setup Instructions

1. **Required Jenkins Plugins**
   - Git plugin
   - Docker plugin
   - Docker Pipeline plugin
   - Pipeline plugin

2. **Jenkins Configuration**
   - Go to Jenkins Dashboard
   - Navigate to "Manage Jenkins" > "Manage Plugins"
   - Install the required plugins if not already installed
   - Restart Jenkins after plugin installation

3. **Create Jenkins Pipeline**
   - Click "New Item" on Jenkins Dashboard
   - Enter a name for your pipeline (e.g., "gizmap-pipeline")
   - Select "Pipeline" and click OK
   - In the pipeline configuration:
     - Under "Pipeline" section, select "Pipeline script from SCM"
     - Select "Git" as SCM
     - Enter Repository URL: https://github.com/arifhidayat65/gizmap.git
     - Specify branch: */main
     - Script Path: Jenkinsfile
   - Click "Save"

4. **First Time Setup**
   - After creating the pipeline, click "Build Now" to start the first build
   - Jenkins will automatically:
     - Clone the repository
     - Build the application
     - Run tests
     - Build Docker image
     - Deploy the application

5. **Automatic Builds**
   - The pipeline will automatically build when changes are pushed to the repository
   - You can also manually trigger builds from the Jenkins dashboard

## Application Access
- The application will be accessible at http://192.168.1.12:8080
- Jenkins interface is available at http://192.168.1.12:8081

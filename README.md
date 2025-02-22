# Gizmap Application

## Jenkins Setup Instructions

1. **Required Jenkins Plugins**
   - Git plugin
   - Docker plugin
   - Docker Pipeline plugin
   - Pipeline plugin
   - SSH Agent plugin

2. **Jenkins Configuration**
   - Go to Jenkins Dashboard
   - Navigate to "Manage Jenkins" > "Manage Plugins"
   - Install the required plugins if not already installed
   - Restart Jenkins after plugin installation

3. **GitHub SSH Credentials Setup**
   - Generate SSH key pair if you don't have one:
     ```bash
     ssh-keygen -t ed25519 -C "your-email@example.com"
     ```
   - Add the public key to your GitHub account:
     - Copy the content of ~/.ssh/id_ed25519.pub
     - Go to GitHub > Settings > SSH and GPG keys > New SSH key
     - Paste your public key and save

   - Add SSH private key to Jenkins:
     - Go to Jenkins Dashboard
     - Navigate to "Manage Jenkins" > "Manage Credentials"
     - Click on "System" and "Global credentials"
     - Click "Add Credentials"
     - Select "SSH Username with private key" from the "Kind" dropdown
     - Set ID as "github-credentials"
     - Set Username as "git"
     - Under "Private Key", select "Enter directly"
     - Paste the content of your private key file (~/.ssh/id_ed25519)
     - Click "OK" to save

4. **Create Jenkins Pipeline**
   - Click "New Item" on Jenkins Dashboard
   - Enter a name for your pipeline (e.g., "gizmap-pipeline")
   - Select "Pipeline" and click OK
   - In the pipeline configuration:
     - Under "Pipeline" section, select "Pipeline script from SCM"
     - Select "Git" as SCM
     - Enter Repository URL: git@github.com:arifhidayat65/be-giz.git
     - Specify branch: */main
     - Script Path: Jenkinsfile
   - Click "Save"

5. **First Time Setup**
   - After creating the pipeline, click "Build Now" to start the first build
   - Jenkins will automatically:
     - Clone the repository
     - Build the application
     - Run tests
     - Build Docker image
     - Deploy the application

6. **Automatic Builds**
   - The pipeline will automatically build when changes are pushed to the repository
   - You can also manually trigger builds from the Jenkins dashboard

## Application Access
- The application will be accessible at http://192.168.1.12:8080
- Jenkins interface is available at http://192.168.1.12:8081

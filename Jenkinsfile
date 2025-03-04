pipeline {
    agent any
    stages {
        stage('Build') { 
            steps {
                //sh 'mvn clean package '
                sh 'apps=$(ls -d */)'
                sh 'for app in $apps; do  ls $app"target" ; done' 
            }
        }
    }
}
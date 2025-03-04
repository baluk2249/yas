pipeline {
    agent any
    stages {
        stage('Build') { 
            steps {
                sh 'mvn clean package -pl cart -am'
                sh 'ls -l cart/target' 
            }
        }
    }
}
pipeline{
    agent any
    stages {
        stage('checkout')
        {
        }
    stage('Build and Test'){
        steps {
            sh 'mvn clean package install -pl cart -am'
            sh 'ls -l cart/target'
        }
    }
    }
}
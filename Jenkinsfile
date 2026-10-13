pipeline { agent any

stages { stage('Checkout') { steps { checkout scm } }

stage('Clean and Test') { steps { bat 'mvn clean test' } post { always { junit allowEmptyResults: true, testResults: '/target/surefire-reports/TEST-*.xml' } } }

stage('Package') { steps { bat 'mvn package -DskipTests' } }

stage('Archive Artifact') { steps { archiveArtifacts artifacts: '/target/*.jar', allowEmptyArchive: true } } } }

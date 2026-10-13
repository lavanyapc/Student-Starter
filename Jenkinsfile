pipeline { agent any

stages { stage('Checkout') { steps { checkout scm } }

stage('Clean and Test') { steps { bat 'call "C:/Program Files/apache-maven-3.10.0/bin/mvn.cmd" clean test' } post { always { junit testResults: 'course-registration-app/target/surefire-reports/TEST-*.xml', allowEmptyResults: false } } }

stage('Package') { steps { bat 'call "C:/Program Files/apache-maven-3.10.0/bin/mvn.cmd" package' } }

stage('Archive Artifact') { steps { archiveArtifacts artifacts: 'course-registration-app/target/*.jar', fingerprint: true } } } }
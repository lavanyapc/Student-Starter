pipeline { agent any

stages { stage('Checkout') { steps { checkout scm } }

stage('Clean and Test') { steps { bat 'mvn -f pom.xml clean test' } post { always { junit 'course-registration-app/target/surefire-reports/TEST-*.xml' } } }

stage('Package') { steps { bat 'mvn -f pom.xml package' } }

stage('Archive Artifact') { steps { archiveArtifacts artifacts: 'course-registration-app/target/*.jar' } } } }
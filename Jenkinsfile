pipeline {
  agent none
  stages {
    stage('Build') {
      agent any
      steps {
        sh '''chmod o+x ./gradlew
./gradlew build'''
      }
    }

    stage('Archive the Artifacts') {
      steps {
        archiveArtifacts 'build/libs/*'
      }
    }

  }
}
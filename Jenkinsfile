pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        sh '''chmod o+x ./gradlew
./gradlew build'''
        archiveArtifacts 'build/libs/*'
      }
    }

  }
}
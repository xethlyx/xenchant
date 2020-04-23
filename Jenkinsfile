pipeline {
  agent any
  stages {
    stage('Build') {
      agent any
      steps {
        catchError() {
          sh '''chmod o+x ./gradlew
./gradlew build'''
          archiveArtifacts 'build/libs/*'
        }

      }
    }

  }
}
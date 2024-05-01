@NonCPS
def getSlackMessage() {
    def duration = currentBuild.durationString.minus(" and counting")
    def message = "Build <${env.BUILD_URL}|#${env.BUILD_NUMBER}> of <https://github.com/eclipse/epsilon/tree/${env.BRANCH_NAME}|${currentBuild.fullProjectName}> "
    
    message += (currentBuild.currentResult == "SUCCESS" ? "passed" : "failed") + " in ${duration}\n\n"

    for (changeSet in currentBuild.changeSets) {
      for (entry in changeSet.items) {
        message += "`${entry.commitId.take(7)}` ${entry.msg} - ${entry.author}\n"
      }
    }
    return message
}

def plainTriggers = '(plugins\\/.*)|(pom[-]plain[.]xml)|(Jenkinsfile)'
def baseTriggers = "${plainTriggers}|(pom\\.xml)"
def updateTriggers = "${baseTriggers}|(standalone\\/.*)|(features\\/.*)|(releng\\/.*(target|updatesite)\\/.*)"

pipeline {
    agent {
      kubernetes {
        label 'migration'
      }
    }
    options {
      disableConcurrentBuilds()
      buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2', daysToKeepStr: '14', numToKeepStr: ''))
    }
    tools {
        maven 'apache-maven-3.9.5'
        jdk 'openjdk-jdk17-latest'
    }
    triggers {
        pollSCM('H/5 * * * *')
    }
    stages {
      stage('Main') {
        stages {
          stage('Build') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${updateTriggers}|(tests\\/.*)"
                expression { return currentBuild.number == 1 }
                triggeredBy 'UserIdCause'
              }
            } 
            steps {
              sh 'mvn -B -T 1C clean install -P eclipse-sign'
            }
          }
          stage('Test') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${baseTriggers}|(tests\\/.*)"
                expression { return currentBuild.number == 1 }
                triggeredBy 'UserIdCause'
              }
            }
            steps {
              wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: false]) {
                sh 'mvn -B -f tests/org.eclipse.epsilon.test verify -P plugged'
              }
              sh 'mvn -B -f tests/org.eclipse.epsilon.test surefire:test -P ci'
            }
          }
          stage('Javadocs') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${baseTriggers}"
                expression { return currentBuild.number == 1 }
                triggeredBy 'UserIdCause'
              }
            }
            steps {
              sh 'mvn -f plugins -B initialize javadoc:aggregate'
            }
          }
          stage('Update site') {
            when {
              allOf {
                branch 'main'
                anyOf {
                  changeset comparator: 'REGEXP', pattern: "${updateTriggers}"
                  expression { return currentBuild.number == 1 }
                  triggeredBy 'UserIdCause'
                }
              }
            }
            steps {
              sh 'mvn -f releng install -P updatesite'
              lock('download-area') {
                sshagent (['projects-storage.eclipse.org-bot-ssh']) {
                  sh '''
                    INTERIM=/home/data/httpd/download.eclipse.org/epsilon/interim
                    SITEDIR="$WORKSPACE/releng/org.eclipse.epsilon.updatesite/target"
                    if [ -d "$SITEDIR" ]; then
                      ssh genie.epsilon@projects-storage.eclipse.org rm -rf $INTERIM
                      scp -r "$SITEDIR/repository" genie.epsilon@projects-storage.eclipse.org:$INTERIM
                      scp "$SITEDIR"/*.zip genie.epsilon@projects-storage.eclipse.org:$INTERIM/epsilon-interim-site.zip
                    fi
                    PROJECT_VERSION=$(grep Bundle-Version plugins/org.eclipse.epsilon.eol.engine/META-INF/MANIFEST.MF | awk '{print $2}' | sed 's/.qualifier//')
                    JAVADOC=/home/data/httpd/download.eclipse.org/epsilon/interim-javadoc
                    JAVADOCDIR="$WORKSPACE/plugins/target/site/apidocs"
                    if [ -d "$JAVADOCDIR" ]; then
                      ssh genie.epsilon@projects-storage.eclipse.org "rm -rf $JAVADOC"
                      scp -r "$JAVADOCDIR" genie.epsilon@projects-storage.eclipse.org:$JAVADOC
                      if echo "$PROJECT_VERSION" | grep -Eq "^[0-9]+.[0-9]+.[0-9]+$"; then
                        JAVADOC_VERSIONED=/home/data/httpd/download.eclipse.org/epsilon/${PROJECT_VERSION}-javadoc
                        ssh genie.epsilon@projects-storage.eclipse.org "rm -rf $JAVADOC_VERSIONED"
                        scp -r "$JAVADOCDIR" genie.epsilon@projects-storage.eclipse.org:$JAVADOC_VERSIONED
                      fi
                    fi
                  '''
                }
              }
            }
          }
          stage('Plain Maven build') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${plainTriggers}"
                expression { return currentBuild.number == 1 }
                branch 'maven-*'
                triggeredBy 'UserIdCause'
              }
            }
            steps {
              sh 'bash plugins/org.eclipse.epsilon.eunit.cmp.emf/download-emf-compare.sh'
              sh 'mvn -B -T 1C -f pom-plain.xml compile'
            }
          }
          stage('Deploy to OSSRH') {
            when {
              anyOf {
                allOf {
                   branch 'main'
                   anyOf {
                     changeset comparator: 'REGEXP', pattern: "${plainTriggers}"
                     expression { return currentBuild.number == 1 }
                     triggeredBy 'UserIdCause'
                   }
                }
                branch 'maven-*'
              }
            }
            environment {
              KEYRING = credentials('secret-subkeys.asc')
            }
            steps {
              sh '''
                gpg --batch --import "${KEYRING}"
                for fpr in $(gpg --list-keys --with-colons  | awk -F: '/fpr:/ {print $10}' | sort -u);
                do
                  echo -e "5\ny\n" |  gpg --batch --command-fd 0 --expert --edit-key $fpr trust;
                done
              '''
              lock('ossrh') {
                sh 'mvn -B -f pom-plain.xml -P ossrh,eclipse-sign deploy'
              }
            }
          }
        }
      }
    }
    post {
      success {
        slackSend (channel: '#ci-notifications', botUser: true, color: '#00FF00', message: getSlackMessage())
      }
      failure {
        slackSend (channel: '#ci-notifications', botUser: true, color: '#FF0000', message: getSlackMessage())
        script {
          if (env.BRANCH_NAME == "main")
            emailext(
              to: 'epsilon-dev@eclipse.org',
              subject: 'Epsilon Interim build failed!',
              body: "${env.BUILD_TAG}. More info at ${env.BUILD_URL}",
              charset: 'UTF-8',
              mimeType: 'text/html'
            )
        }
      }
    }
}

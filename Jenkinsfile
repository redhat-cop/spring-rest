#!/usr/bin/groovy


node (''){
    env.DEV_PROJECT = env.OPENSHIFT_BUILD_NAMESPACE
    env.SOURCE_CONTEXT_DIR = ""
    env.UBER_JAR_CONTEXT_DIR = "target/"
    env.MVN_COMMAND = "clean package spring-boot:repackage -DskipTests"
    env.APP_NAME = "${env.JOB_NAME}".replaceAll(/-?${env.PROJECT_NAME}-?/, '').replaceAll(/-?pipeline-?/, '').replaceAll('/','')
	echo env.APP_NAME
    env.OCP_API_SERVER = "${env.OPENSHIFT_API_URL}"
    env.OCP_TOKEN = readFile('/var/run/secrets/kubernetes.io/serviceaccount/token').trim()
    env.MVN_RELEASE_DEPLOYMENT_REPOSITORY = "nexus::default::http://nexus:8081/repository/maven-releases"
	env.PREPROD_PROJECT="roridedi-demo"
	env.APP_DEV="roridedi-dev"
	env.MVN_SNAPSHOT_DEPLOYMENT_REPOSITORY = "nexus::default::http://nexus-roridedi-ci-cd.apps.s9.core.rht-labs.com/repository/maven-snapshots"
    env.MVN_RELEASE_DEPLOYMENT_REPOSITORY = "nexus::default::http://nexus-roridedi-ci-cd.apps.s9.core.rht-labs.com/repository/maven-releases/"
    env.sonarHost = "sonarqube-roridedi-ci-cd.apps.s9.core.rht-labs.com"
    env.groupId    = getGroupIdFromPom("pom.xml")
	env.artifactId = getArtifactIdFromPom("pom.xml")
	env.version    = getVersionFromPom("pom.xml")
	env.packageType    = getPackagingFromPom("pom.xml")
	env.devTag  = "${version}-${BUILD_NUMBER}"
}



node('jenkins-slave-mvn') {

  stage('SCM Checkout') {
    checkout scm
  }

  stage('Build App') {
	sh "mvn ${env.MVN_COMMAND} -s ./nexus_settings.xml"
	pom = readMavenPom file: 'pom.xml'
	echo 'POM VERSION ${pom.version}'
	
  }
    stage('Unit Tests') {
    echo "Running Unit Tests"
    sh "${mvnCmd} test"
  }

  stage('Code Analysis') {
    echo "Running Code Analysis"
    sh "${mvnCmd} sonar:sonar -Dsonar.host.url=http://${env.sonarHost} -Dsonar.projectName=${JOB_BASE_NAME}-${env.devTag}"
  }
  
  stage('Build Image') {
	sh "oc start-build ${env.APP_NAME} --from-dir=${env.UBER_JAR_CONTEXT_DIR} --follow"

	sh "oc tag java-app:latest java-app:${pom.version}"
  }

  // no user changes should be needed below this point
  stage ('Deploy to Dev') {
    openshiftTag (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", destStream: "${env.APP_NAME}", destTag: "${pom.version}", destinationAuthToken: "${env.OCP_TOKEN}", destinationNamespace: "${env.APP_DEV}", namespace: "${env.DEV_PROJECT}", srcStream: "${env.APP_NAME}", srcTag: "${pom.version}")

    openshiftVerifyDeployment (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", depCfg: "${env.APP_NAME}", namespace: "${env.DEV_PROJECT}", verifyReplicaCount: true)
  }
	  stage ('Deploy to PreProd') {
    input "Promote Application to PreProd?"
    openshiftTag (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", destStream: "${env.APP_NAME}", destTag: "${pom.version}", destinationAuthToken: "${env.OCP_TOKEN}", destinationNamespace: "${env.PREPROD_PROJECT}", namespace: "${env.DEV_PROJECT}", srcStream: "${env.APP_NAME}", srcTag: "${pom.version}")

    openshiftVerifyDeployment (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", depCfg: "${env.APP_NAME}", namespace: "${env.PREPROD_PROJECT}", verifyReplicaCount: true)

  }
}
// Convenience Functions to read variables from the pom.xml
// Do not change anything below this line.
// --------------------------------------------------------
def getVersionFromPom(pom) {
  def matcher = readFile(pom) =~ '<version>(.+)</version>'
  matcher ? matcher[0][1] : null
}

def getGroupIdFromPom(pom) {
  def matcher = readFile(pom) =~ '<groupId>(.+)</groupId>'
  matcher ? matcher[0][1] : null
}

def getArtifactIdFromPom(pom) {
  def matcher = readFile(pom) =~ '<artifactId>(.+)</artifactId>'
  matcher ? matcher[0][1] : null
}

def getPackagingFromPom(pom) {
  def matcher = readFile(pom) =~ '<packaging>(.+)</packaging>'
  matcher ? matcher[0][1] : null
}

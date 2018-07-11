#!/usr/bin/groovy


node (''){
    env.SOURCE_CONTEXT_DIR = ""
    env.UBER_JAR_CONTEXT_DIR = "target/"
    env.MVN_COMMAND = "clean package spring-boot:repackage -DskipTests"
	env.APP_name="spring-rest"
	echo env.APP_NAME
    env.OCP_API_SERVER = "${env.OPENSHIFT_API_URL}"
    env.OCP_TOKEN = readFile('/var/run/secrets/kubernetes.io/serviceaccount/token').trim()
	env.PREPROD_PROJECT="roridedi-demo"
	env.APP_DEV="basic-spring-boot-dev"
	env.APP_STAGE="basic-spring-boot-stage"
	env.APP_PROD="basic-spring-boot-prod"
	env.APP_BUILD="basic-spring-boot-build"
}



node('maven') {

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
    sh "mvn ${env.MVN_COMMAND} test"
  }
  
  stage('Build Image') {
	sh "oc start-build ${env.APP_NAME} --from-dir=${env.UBER_JAR_CONTEXT_DIR} --follow"

  }

  // no user changes should be needed below this point
 	stage ('Deploy to Dev') {
	 sh "oc tag spring-rest:latest spring-rest:${pom.version}"

   
	openshiftTag (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", destStream: "${env.APP_NAME}", destTag: 'latest', destinationAuthToken: "${env.OCP_TOKEN}", destinationNamespace: "${env.APP_DEV}", namespace: "${env.APP_BUILD}", srcStream: "${env.APP_NAME}", srcTag: 'latest')

	openshiftVerifyDeployment (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", depCfg: "${env.APP_NAME}", namespace: "${env.APP_DEV}", verifyReplicaCount: true)
   
   
    }
	  stage ('Deploy to Stage') {
    openshiftTag (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", destStream: "${env.APP_NAME}", destTag: "latest", destinationAuthToken: "${env.OCP_TOKEN}", destinationNamespace: "${env.APP_STAGE}", namespace: "${env.APP_DEV}", srcStream: "${env.APP_NAME}", srcTag: "latest")
    openshiftVerifyDeployment (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", depCfg: "${env.APP_NAME}", namespace: "${env.APP_STAGE}", verifyReplicaCount: true)

  }
  	  stage ('Deploy to Prod') {
   input "Promote Application to Prod?"
    openshiftTag (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", destStream: "${env.APP_NAME}", destTag: "latest", destinationAuthToken: "${env.OCP_TOKEN}", destinationNamespace: "${env.APP_PROD}", namespace: "${env.APP_STAGE}", srcStream: "${env.APP_NAME}", srcTag: "latest")
    openshiftVerifyDeployment (apiURL: "${env.OCP_API_SERVER}", authToken: "${env.OCP_TOKEN}", depCfg: "${env.APP_NAME}", namespace: "${env.APP_PROD}", verifyReplicaCount: true)

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

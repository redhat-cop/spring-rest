#!/usr/bin/groovy


node (''){
    env.DEV_PROJECT = env.OPENSHIFT_BUILD_NAMESPACE
    env.SOURCE_CONTEXT_DIR = ""
    env.UBER_JAR_CONTEXT_DIR = "target/"
    env.MVN_COMMAND = "clean install"
    env.APP_NAME = "${env.JOB_NAME}".replaceAll(/-?${env.PROJECT_NAME}-?/, '').replaceAll(/-?pipeline-?/, '').replaceAll('/','')
	echo env.APP_NAME
    env.OCP_API_SERVER = "${env.OPENSHIFT_API_URL}"
    env.OCP_TOKEN = readFile('/var/run/secrets/kubernetes.io/serviceaccount/token').trim()
    env.MVN_RELEASE_DEPLOYMENT_REPOSITORY = "nexus::default::http://nexus:8081/repository/maven-releases"
	env.PREPROD_PROJECT="roridedi-demo"
	env.APP_DEV="roridedi-dev"
}



node('jenkins-slave-mvn') {

  stage('SCM Checkout') {
    checkout scm
  }

  stage('Build App') {
	sh "mvn ${env.MVN_COMMAND} "
	pom = readMavenPom file: 'pom.xml'
	echo 'POM VERSION' ${pom.version}
	
  }
  stage('Build Image') {
	sh "oc start-build ${env.APP_NAME} --from-dir=${env.UBER_JAR_CONTEXT_DIR} --follow"
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


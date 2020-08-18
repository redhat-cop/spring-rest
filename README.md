[![License](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)]() ![Java CI with Maven](https://github.com/redhat-cop/spring-rest/workflows/Java%20CI%20with%20Maven/badge.svg)

# Spring Rest - A RESTful API written in Spring Boot

This is a simple app using Spring Boot as part of [Red Hat OpenShift Application Runtimes](https://middlewareblog.redhat.com/2017/05/05/red-hat-openshift-application-runtimes-and-spring-boot-details-you-want-to-know/).

## Usage to Run Locally

1. `git clone`
2. `mvn spring-boot:run`

## Test Endpoints

1. `curl -v http://localhost:8080/v1/greeting`

2. `curl -v http://localhost:8080/v1/hostinfo`

3. `curl -v http://localhost:8080/v1/envinfo`

## Added Plugins for Quality and Security

1. SonarQube Scanner Plugin. *NOTE: This plugin is a client and needs to connect to a running sonar server.*
	- Usage:
	  - `mvn sonar:sonar` to execute. 
	  - Plugin is currently not tied to the standard maven lifecyle.
	- To help execute the scan in Jenkins pipelines, the `sonarqubeStaticAnalysis()` function from the [pipeline-library](https://github.com/redhat-cop/pipeline-library) will execute the goal in your build process.
	- [SonarQube plugin docs](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner+for+Maven)

2. Jacoco Maven Plugin
	- Usage:
		- `mvn package` to execute in standard maven lifecycle
		- `mvn jacoco:report` to execute the plugin standalone
	- Code coverage reports will then be found in `target/site`.
	- [Jacoco plugin docs](https://www.eclemma.org/jacoco/trunk/doc/maven.html)

3. OWASP Dependency Check
	- Usage:
	  - `mvn verify` to execute in standard maven lifecycle
	  - `mvn dependency-check:check` to execute the plugin standalone
	- The dependency-check plugin will check dependencies for vulnerabilities against the National Vulnerability Database hosted by NIST and fail should there be any dependency with a [CVSS score](https://searchsecurity.techtarget.com/definition/CVSS-Common-Vulnerability-Scoring-System) greater or equal to that specified in the pom file.
	- [Maven plugin docs](https://jeremylong.github.io/DependencyCheck/dependency-check-maven/)
	- [OWASP Dependency Check docs](https://www.owasp.org/index.php/OWASP_Dependency_Check)
	- [Continuous security and OWASP Dependency Check blog](https://blog.lanyonm.org/articles/2015/12/22/continuous-security-owasp-java-vulnerability-check.html)
	

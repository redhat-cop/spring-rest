# Spring Rest - A RESTful API written in Spring Boot

This is a simple app using Spring Boot as part of [Red Hat OpenShift Application Runtimes](https://middlewareblog.redhat.com/2017/05/05/red-hat-openshift-application-runtimes-and-spring-boot-details-you-want-to-know/).

## Usage

1. `git clone`
2. `mvn spring-boot:run`

## OpenAPI (formerly known as Swagger) Support

The app uses [Spring Fox](http://springfox.github.io/springfox/) to generate an [OpenAPI spec](https://www.openapis.org/). You can view the spec at /swagger.json or [Swagger UI](https://swagger.io/swagger-ui/) at /swagger-ui.html. 

## Added plugins

1. sonarqube scanner plugin
	- https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner+for+Maven
	- If sonarqube is running in your openshift cluster, use the `sonarqubeStaticAnalysis()` function from the [pipeline-library](https://github.com/redhat-cop/pipeline-library) to execute the `mvn sonar:sonar` goal in your build process.

2. jacoco maven plugin
	- https://www.eclemma.org/jacoco/trunk/doc/maven.html
	- The Jacoco report goal is bound to the package phase (execute `mvn jacoco:report` to generate the reports using the plugin directly). Code coverage reports will then be found in the target/site.

3. owasp dependency check
	- https://jeremylong.github.io/DependencyCheck/dependency-check-maven/
	- https://www.owasp.org/index.php/OWASP_Dependency_Check
	- https://blog.lanyonm.org/articles/2015/12/22/continuous-security-owasp-java-vulnerability-check.html
	- The dependency-check plugin is tied to the verify phase (execute `mvn dependency-check:check` to do a standalone check). This will check dependencies for vulnerabilities against the National Vulernability Database hosted by NIST and fail should there be any dependency with a CVSS score greater or equal to that specified in the pom file. 4 is considered medium to high severity.
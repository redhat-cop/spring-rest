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

2. jacoco maven plugin
	- https://www.eclemma.org/jacoco/trunk/doc/maven.html

3. owasp dependency check
	- https://jeremylong.github.io/DependencyCheck/dependency-check-maven/
	- https://www.owasp.org/index.php/OWASP_Dependency_Check
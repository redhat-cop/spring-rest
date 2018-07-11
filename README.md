# Spring Rest - A RESTful API written in Spring Boot

This is a simple app using Spring Boot as part of [Red Hat OpenShift Application Runtimes](https://middlewareblog.redhat.com/2017/05/05/red-hat-openshift-application-runtimes-and-spring-boot-details-you-want-to-know/).

## Usage

1. `git clone`
2. `mvn spring-boot:run`

## OpenAPI (formerly known as Swagger) Support

The app uses [Spring Fox](http://springfox.github.io/springfox/) to generate an [OpenAPI spec](https://www.openapis.org/). You can view the spec at /swagger.json or [Swagger UI](https://swagger.io/swagger-ui/) at /swagger-ui.html. 

## Running Locally

1. `git clone`
2. `cd spring-rest/` 
3. `mvn spring-boot:run`
4. `open locahost:8080 on your browser`


## Running On Openshift

1.`complete the steps outlined here https://github.com/redhat-cop/container-pipelines/tree/master/basic-spring-boot`

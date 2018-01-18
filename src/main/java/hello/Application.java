package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.metrics.export.prometheus.EnablePrometheusMetrics;

import com.google.common.base.Predicate;

import hello.utils.SimpleCORSFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static springfox.documentation.builders.PathSelectors.*;
import static com.google.common.base.Predicates.*;

@SpringBootApplication
@EnablePrometheusMetrics
@EnableSwagger2
@ComponentScan(basePackageClasses = {
            GreetingController.class,
            SimpleCORSFilter.class
        })
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);    
    }
        
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .select()
            .paths(apiPaths())
            .build();
    }
    

    private Predicate<String> apiPaths() {
        return or(
                regex("/v2/greeting.*"),
                regex("/v2/hostinfo.*"),
                regex("/v2/envinfo.*"),
                regex("/v2/*")
        );
    }    

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Openshift uncontained.io Sample REST API")
            .description("A sample REST API to demonstrate Spring Boot Applications in Openshift.")
            .license("Apache License Version 2.0")
            .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
            .version("2.0")
            .build();
    }
}

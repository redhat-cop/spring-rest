package hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;

/*
import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

//import static springfox.documentation.builders.PathSelectors.*;
//import static com.google.common.base.Predicates.*;

@SpringBootApplication
//@EnableSwagger2
@ComponentScan(basePackages = {"hello"})
public class Application {

	@Value("${shift.rest.disableValidator}")
	private boolean disableValidator;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);    
    }
/*
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .select()
            .paths(apiPaths())
            .build();
    }
    */
/*

    @Bean
    UiConfiguration uiConfig() {

        UiConfigurationBuilder builder = UiConfigurationBuilder.builder();

        if(disableValidator) {
            builder.validatorUrl("");
        }

        return builder.build();
    }
*/

/*

    private Predicate<String> apiPaths() {
        return or(
                regex("/v1/greeting.*"),
                regex("/v1/hostinfo.*"),
                regex("/v1/envinfo.*"),
                regex("/v1/*")
        );
    }
*/
/*
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Openshift uncontained.io Sample REST API")
            .description("A sample REST API to demonstrate Spring Boot Applications in Openshift.")
            .license("Apache License Version 2.0")
            .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
            .version("2.0")
            .build();
    }
    */
}

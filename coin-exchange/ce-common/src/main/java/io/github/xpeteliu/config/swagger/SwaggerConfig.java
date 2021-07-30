package io.github.xpeteliu.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

    final SwaggerProperties swaggerProperties;

    public SwaggerConfig(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("io.github.xpeteliu.controller"))
                .paths(PathSelectors.any())
                .build();
        docket.securitySchemes(getSecuritySchemes())
                .securityContexts(getSecurityContexts());
        return docket;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact(swaggerProperties.getContactName(),
                        swaggerProperties.getContactUrl(),
                        swaggerProperties.getContactEmail()))
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .build();
    }

    private List<SecurityScheme> getSecuritySchemes() {
        return List.of(new ApiKey("Authorization", "Authorization", "Authorization"));
    }

    private List<SecurityContext> getSecurityContexts() {
        List<SecurityReference> securityReferences = Arrays.asList(new SecurityReference("Authorization",
                new AuthorizationScope[]{new AuthorizationScope("global", "AccessAllResources")}));
        return Arrays.asList(new SecurityContext(securityReferences, (each) -> true, (each) -> true, (each) -> true));
    }
}

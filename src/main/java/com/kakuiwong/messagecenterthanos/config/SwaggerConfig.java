package com.kakuiwong.messagecenterthanos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: gaoyang
 * @Description:
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo()).groupName("default")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kakuiwong.messagecenterthanos"))
                .paths(PathSelectors.any()).build().securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("message-center-thanos",
                "消息处理中心",
                "1.0",
                "", new Contact("gy", "", "785175323@qq.com"),
                "MIT License",
                "MIT License",
                new ArrayList()
        );

        return apiInfo;
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> authorizationTypes = Arrays.asList(new ApiKey("token", "token", "header"));
        return authorizationTypes;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> list = new ArrayList<>();
        list.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build());
        return list;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> list = new ArrayList<>();
        list.add(new SecurityReference("token", authorizationScopes));
        return list;
    }
}

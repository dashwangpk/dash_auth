package com.sprint.dash.server.system.configure;

import com.sprint.dash.server.system.properties.DashServerSystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class DashWebConfigure {

    @Autowired
    private DashServerSystemProperties dashServerSystemProperties;

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(dashServerSystemProperties.getSwagger().getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                dashServerSystemProperties.getSwagger().getTitle(),
                dashServerSystemProperties.getSwagger().getDescription(),
                dashServerSystemProperties.getSwagger().getVersion(),
                null,
                new Contact(dashServerSystemProperties.getSwagger().getAuthor(),
                        dashServerSystemProperties.getSwagger().getUrl(),
                        dashServerSystemProperties.getSwagger().getEmail()),
                dashServerSystemProperties.getSwagger().getLicense(),
                dashServerSystemProperties.getSwagger().getLicenseUrl(), Collections.emptyList());
    }

}

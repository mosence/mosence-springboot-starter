package org.mosence.project.configurations.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger文档配置
 *
 * @author ：mosence
 * @date ：2019/03/20
 */
@EnableSwagger2
@ConditionalOnClass(Docket.class)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerResourceConfiguration{

    private static final String BASIC_AUTH = "basic";

    private static final String OAUTH2_AUTH = "oauth2";

    private final SwaggerProperties swaggerProperties;

    public SwaggerResourceConfiguration(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    @ConditionalOnMissingBean(Docket.class)
    public Docket docket() {
        List<SecurityScheme> securitySchemes = new ArrayList<>(0);
        if (!swaggerProperties.getAuth().isEmpty()) {
            for (SwaggerProperties.SwaggerAuth swaggerAuth : swaggerProperties.getAuth()) {
                String type = swaggerAuth.getType();
                String authUrl = swaggerAuth.getAuthUrl();
                if (BASIC_AUTH.equals(type)) {
                    securitySchemes.add(new BasicAuth(authUrl));
                }
                if (OAUTH2_AUTH.equals(type)) {
                    securitySchemes.add(new OAuth(
                            "oauth2",
                            new ArrayList<>(0),
                            new ArrayList<GrantType>() {{
                                add(new ResourceOwnerPasswordCredentialsGrant(authUrl));
                            }}));
                }
            }
        }
        List<SecurityContext> securityContexts = new ArrayList<>(0);
        List<SecurityReference> securityReferences = new ArrayList<>(0);
        SecurityReference securityReference =
                SecurityReference.builder()
                        .reference("oauth2")
                        .scopes(new AuthorizationScope[]{})
                        .build();
        securityReferences.add(securityReference);
        securityContexts.add(
                SecurityContext.builder()
                        .forPaths(PathSelectors.any())
                        .securityReferences(securityReferences)
                        .build());


        ApiInfo info = new ApiInfo(
                swaggerProperties.getName(),
                swaggerProperties.getDescription(),
                swaggerProperties.getVersion(),
                "",
                new Contact("", "", ""),
                "",
                "",
                new ArrayList<VendorExtension>() {{
                }});

        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(securityContexts)
                .securitySchemes(securitySchemes)
                .apiInfo(info)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiScanBasePackage()))
                .build();
    }

}

package org.mosence.project.configurations.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：mosence
 * @date ：2019/03/28
 */
@ConditionalOnEnabledEndpoint(endpoint = AbstractSwaggerApiEndpoint.class)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerApiEndpointConfigure {
    private final SwaggerProperties swaggerProperties;

    @Value("${spring.application.name}")
    private String appName;

    public SwaggerApiEndpointConfigure(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass({HttpServletRequest.class})
    @ConditionalOnBean({DocumentationCache.class, ServiceModelToSwagger2Mapper.class})
    public AbstractSwaggerApiEndpoint servletSwaggerApiEndpoint(DocumentationCache documentationCache,
                                                                ServiceModelToSwagger2Mapper mapper) {
        return new ServletSwaggerApiEndpoint(documentationCache,mapper,swaggerProperties,appName);
    }

}

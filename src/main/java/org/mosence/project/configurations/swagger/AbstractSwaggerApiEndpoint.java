package org.mosence.project.configurations.swagger;

import io.swagger.models.Swagger;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

/**
 * @author ：mosence
 * @date ：2019/03/29
 */
@Endpoint(id = "api-docs")
@Component
public abstract class AbstractSwaggerApiEndpoint {
    static final String X_FORWARDED_PREFIX = "X-Forwarded-Prefix";
    protected DocumentationCache documentationCache;
    protected ServiceModelToSwagger2Mapper mapper;
    protected SwaggerProperties swaggerProperties;

    public AbstractSwaggerApiEndpoint(DocumentationCache documentationCache,
                                      ServiceModelToSwagger2Mapper mapper,
                                      SwaggerProperties swaggerProperties) {
        this.documentationCache = documentationCache;
        this.mapper = mapper;
        this.swaggerProperties = swaggerProperties;
    }

    @ReadOperation(produces = "application/json")
    public Swagger apiDocs(){
        return loadApiDocs();
    }

    /**
     * ApiDocs 的构造方法
     * @return Swagger ApiDocs 文档
     */
    abstract Swagger loadApiDocs();
}

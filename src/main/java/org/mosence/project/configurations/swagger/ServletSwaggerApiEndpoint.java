package org.mosence.project.configurations.swagger;

import com.google.common.base.Strings;
import io.swagger.models.Swagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static springfox.documentation.swagger.common.HostNameProvider.componentsFrom;

/**
 * @author ：mosence
 * @date ：2019/03/28
 */

@Slf4j
public class ServletSwaggerApiEndpoint extends AbstractSwaggerApiEndpoint {

    @Resource
    private HttpServletRequest servletRequest;

    private String appName;

    public ServletSwaggerApiEndpoint(DocumentationCache documentationCache, ServiceModelToSwagger2Mapper mapper, SwaggerProperties swaggerProperties,String appName) {
        super(documentationCache, mapper, swaggerProperties);
        this.appName = appName;
    }
    @Override
    Swagger loadApiDocs() {
        String groupName = Docket.DEFAULT_GROUP_NAME;
        Documentation documentation = documentationCache.documentationByGroup(groupName);
        Swagger swagger = mapper.mapDocumentation(documentation);

        if(Strings.isNullOrEmpty(swaggerProperties.getGatewayUrl())){
            UriComponents uriComponents = componentsFrom(servletRequest, swagger.getBasePath());
            swagger.basePath(Strings.isNullOrEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());
            swagger.setHost(uriComponents.getHost()+ ":" +uriComponents.getPort());
        }else{
            String basePathUri = swaggerProperties.getGatewayUrl()+ "/" +
                    (Strings.isNullOrEmpty(swaggerProperties.getGatewayServerForward())? appName: swaggerProperties.getGatewayServerForward());
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(basePathUri).build();
            swagger.basePath(Strings.isNullOrEmpty(uriComponents.getPath()) ? "/" : uriComponents.getPath());
            swagger.setHost(uriComponents.getHost() + ":" + uriComponents.getPort());
        }

        return swagger;
    }
}

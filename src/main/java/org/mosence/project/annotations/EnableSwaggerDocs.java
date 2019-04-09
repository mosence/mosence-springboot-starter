package org.mosence.project.annotations;

import org.mosence.project.configurations.swagger.SwaggerApiEndpointConfigure;
import org.mosence.project.configurations.swagger.SwaggerResourceConfiguration;
import org.mosence.project.configurations.swagger.SwaggerWebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 启用API文档配置
 * <p>使用 配置 eking.swagger.* 简易配置 swagger-api文档，并配置spring-security允许/v2/api-docs端点</p>
 * @author ：mosence
 * @date ：2019/03/20
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Configuration
@Import({SwaggerResourceConfiguration.class,
        SwaggerWebSecurityConfiguration.SwaggerOauth2ResourcePermitAllAdapter.class,
        SwaggerWebSecurityConfiguration.SwaggerOauth2WebPermitAllAdapter.class,
        SwaggerApiEndpointConfigure.class})
public @interface EnableSwaggerDocs {
}

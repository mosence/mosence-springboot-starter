package org.mosence.project.configurations.swagger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ：msoence
 * @date ：2019/03/20
 */
@ConfigurationProperties(prefix = "eking.swagger")
@Data
public class SwaggerProperties {

    /**
     * 网关路径
     */
    private String gatewayUrl;
    /**
     * 网关中访问到该服务的ID
     */
    private String gatewayServerForward;
    /**
     * 文档名称
     */
    private String name = "Swagger-Api-Docs";

    /**
     * API版本
     */
    private String version = "v1";

    /**
     * API描述
     */
    private String description = "Swagger API docs";

    /**
     * API扫描包
     */
    private String apiScanBasePackage = "com.eking";

    /**
     * 用户认证描述
     */
    private Set<SwaggerAuth> auth = new HashSet<>(0);

    @Data
    @EqualsAndHashCode(of = {"type"})
    public static class SwaggerAuth{
        /**
         * 认证类型
         * <p>basic : 简单认证</p>
         * <p>oauth2 : oauth2认证，需要在authUrl中配置 access_token_url</p>
         */
        private String type = "basic";
        /**
         * 认证地址，默认为 /login
         */
        private String authUrl = "/login";
    }
}

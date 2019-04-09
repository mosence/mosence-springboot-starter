package org.mosence.project.configurations.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import springfox.documentation.swagger2.web.Swagger2Controller;

/**
 * @author ：mosence
 * @date ：2019/03/20
 */
public class SwaggerWebSecurityConfiguration {
    @ConditionalOnClass(ResourceServerConfiguration.class)
    public static class SwaggerOauth2ResourcePermitAllAdapter extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers(Swagger2Controller.DEFAULT_URL).permitAll();
        }
    }
    @ConditionalOnClass(WebSecurityConfiguration.class)
    @Order(99)
    public static class SwaggerOauth2WebPermitAllAdapter extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(Swagger2Controller.DEFAULT_URL);
        }
    }
}
